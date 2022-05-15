package org.dragonitemc.dragonshop.services;

import com.ericlam.mc.eld.misc.DebugLogger;
import com.ericlam.mc.eld.services.LoggingService;
import com.ericlam.mc.eld.services.ScheduleService;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.DragonShop;
import org.dragonitemc.dragonshop.ShopException;
import org.dragonitemc.dragonshop.api.*;
import org.dragonitemc.dragonshop.config.DragonShopMessage;
import org.dragonitemc.dragonshop.config.Shop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ShopTaskManager implements ShopTaskService {

    @Inject
    private DragonShop plugin;

    @Inject
    private DragonShopMessage message;

    @Inject
    private ScheduleService scheduleService;

    private final Map<String, RewardTask<?>> rewardTasks = new ConcurrentHashMap<>();
    private final Map<String, PriceTask<?>> priceTasks = new ConcurrentHashMap<>();

    private final Map<String, Condition<?>> conditionMap = new ConcurrentHashMap<>();

    private final DebugLogger logger;

    @Inject
    public ShopTaskManager(LoggingService loggingService){
        this.logger = loggingService.getLogger(ShopTaskService.class);
    }

    public CompletableFuture<Void> handleTask(Player player, Shop.ClickHandle handle) {

        CompletableFuture<PurchaseResult> future = CompletableFuture.completedFuture(PurchaseResult.success());

        Map<PriceTask<?>, Shop.PriceInfo> priceTasks = new LinkedHashMap<>();

        List<Supplier<CompletableFuture<Void>>> rollbackTasks = new ArrayList<>();

        for (Shop.PriceInfo priceInfo : Optional.ofNullable(handle.prices).orElse(List.of())) {
            var task = this.priceTasks.get(priceInfo.type);
            if (task == null) {
                throw new ShopException("設置錯誤", "無效的 PriceType: " + priceInfo.type);
            }
            priceTasks.put(task, priceInfo);
        }


        Map<RewardTask<?>, Shop.RewardInfo> rewardTasks = new LinkedHashMap<>();
        for (Shop.RewardInfo rewardInfo : Optional.ofNullable(handle.rewards).orElse(List.of())) {
            var task = this.rewardTasks.get(rewardInfo.type);
            if (task == null) {
                throw new ShopException("設置錯誤", "無效的 RewardType: " + rewardInfo.type);
            }
            rewardTasks.put(task, rewardInfo);
        }

        for (PriceTask<?> task : priceTasks.keySet()) {
            var content = priceTasks.get(task);
            future = future.thenCompose(lastResult -> {
                logger.debug("processing price task {0}", task.getName());
                // 前面失敗可立刻返回
                if (!lastResult.isSuccess()) {
                    logger.debug("lastResult is not success, return");
                    return CompletableFuture.completedFuture(lastResult);
                }
                return doPurchase(player, task, content.price).thenApply(result -> {
                    if (result.isSuccess()) {
                        rollbackTasks.add(() -> doRollback(player, task, content.price));
                    } else if (content.failedMessage != null) {
                        result.setMessage(content.failedMessage);
                    }
                    logger.debug("price task {0} result: {1}", task.getName(), result);
                    return result;
                });
            });
        }

        CompletableFuture<Boolean> priceTask = future.thenCompose(purchaseResult -> {

            if (!purchaseResult.isSuccess()) {

                logger.debug("Purchase failed for player {0}: {1}", player.getName(), purchaseResult.getMessage());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', purchaseResult.getMessage()));
                logger.debug("doing all {0} rollback tasks", rollbackTasks.size());
                return CompletableFuture.allOf(rollbackTasks.stream().map(Supplier::get).toArray(CompletableFuture[]::new)).thenApply(v -> false);

            } else if (!priceTasks.isEmpty()) {
                logger.debug("Purchase success for player {0}", player.getName());
                // TODO custom message
                player.sendMessage("購買成功");
            }

            return CompletableFuture.completedFuture(true);

        });

        if (rewardTasks.isEmpty()) {
            logger.debug("reward is empty, returned");
            return priceTask.thenAccept(success -> {
            });
        }

        List<Supplier<CompletableFuture<Void>>> rewardFutures = new ArrayList<>();
        for (RewardTask<?> task : rewardTasks.keySet()) {
            var content = rewardTasks.get(task);
            rewardFutures.add(() -> doReward(player, task, content.reward));
        }

        return priceTask.thenCompose(success -> {
            if (success) {
                logger.debug("purchase result is success, doing all {0} rewards tasks", rewardFutures.size());
                return CompletableFuture.allOf(rewardFutures.stream().map(Supplier::get).toArray(CompletableFuture[]::new));
            } else {
                logger.debug("purchase result is failed");
                return CompletableFuture.completedFuture(null);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private <T> CompletableFuture<Void> doRollback(Player player, PriceTask<T> task, Object content) {
        T price = (T) content;
        if (task instanceof AsyncPriceTask<T> at) {
            return at.doRollBackAsync(price, player);
        } else {
            task.doRollBack(price, player);
            return CompletableFuture.completedFuture(null);
        }
    }


    @SuppressWarnings("unchecked")
    private <T> CompletableFuture<PurchaseResult> doPurchase(Player player, PriceTask<T> task, Object content) {
        try {
            T price = (T) content;
            if (task instanceof AsyncPriceTask<T> at) {
                return at.doPurchaseAsync(price, player);
            } else {
                var result = task.doPurchase(price, player);
                return CompletableFuture.completedFuture(result);
            }
        }catch (ClassCastException e){
            throw new ShopException("設置錯誤", "價格類型 " + task.getName() + " 不接受這個類型: " + content.getClass().getName());
        }


    }

    @SuppressWarnings("unchecked")
    private <T> CompletableFuture<Void> doReward(Player player, RewardTask<T> task, Object content) {
        try {
            T reward = (T) content;
            if (task instanceof AsyncRewardTask<T> at) {
                return at.giveRewardAsync(reward, player);
            } else {
                task.giveReward(reward, player);
                return CompletableFuture.completedFuture(null);
            }

        }catch (ClassCastException e){
            throw new ShopException("設置錯誤", "獎勵類型 " + task.getName() + " 不接受這個類型: " + content.getClass().getName());
        }


    }

    @Override
    public void addRewardTask(RewardTask<?> rewardTask) {
        plugin.getLogger().info("成功註冊 獎勵類型: " + rewardTask.getName());
        this.rewardTasks.put(rewardTask.getName(), rewardTask);
    }

    @Override
    public void addPriceTask(PriceTask<?> priceTask) {
        plugin.getLogger().info("成功註冊 價格類型: " + priceTask.getName());
        this.priceTasks.put(priceTask.getName(), priceTask);
    }

    @Override
    public void addCondition(Condition<?> condition) {
        plugin.getLogger().info("成功註冊 條件類型: " + condition.getName());
        this.conditionMap.put(condition.getName(), condition);
    }

    @Override
    public boolean isPassCondition(Player player, Shop.ShopItemInfo info) {
        if (info.conditions == null || info.conditions.isEmpty()) return true;
        for (var condition : info.conditions) {
            var task = conditionMap.get(condition.type);
            if (task == null) {
                throw new ShopException("設置錯誤", "條件類型 " + condition.type + " 不存在");
            }
            var matched = checkIsMatched(task, condition.condition, player) == !condition.revert;
            if (!matched) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Set<String> getDisabledItemIds(Player player, Stream<Map.Entry<String, List<Shop.ConditionInfo>>> items) {
        var disabled = new HashSet<String>();
        items.forEach(entry -> {
            var itemId = entry.getKey();
            var conditions = entry.getValue();
            for (Shop.ConditionInfo condition : conditions) {
                var task = conditionMap.get(condition.type);
                if (task == null) {
                    throw new ShopException("設置錯誤", "條件類型 " + condition.type + " 不存在");
                }
                var matched = checkIsMatched(task, condition.condition, player) == !condition.revert;
                if (!matched) {
                    disabled.add(itemId);
                    break;
                }
            }
        });
        return disabled;
    }

    @SuppressWarnings("unchecked")
    private <T> boolean checkIsMatched(Condition<T> condition, Object content, Player player) {
        try {
            var cond = (T) content;
            return condition.isMatched(cond, player);
        } catch (ClassCastException e) {
            throw new ShopException("設置錯誤", "條件類型 " + condition.getName() + " 不接受這個類型: " + content.getClass().getName());
        }
    }


}
