package org.dragonitemc.dragonshop.services;

import com.ericlam.mc.eld.services.ScheduleService;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.DragonShop;
import org.dragonitemc.dragonshop.ShopException;
import org.dragonitemc.dragonshop.api.*;
import org.dragonitemc.dragonshop.config.DragonShopMessage;
import org.dragonitemc.dragonshop.config.Shop;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
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

    public CompletableFuture<Void> handleTask(Player player, Shop.ClickHandle handle) {

        CompletableFuture<PurchaseResult> future = CompletableFuture.completedFuture(PurchaseResult.success());

        Map<PriceTask<?>, Shop.PriceInfo> priceTasks = new LinkedHashMap<>();
        List<CompletableFuture<Void>> rollbackTasks = new ArrayList<>();

        for (Shop.PriceInfo priceInfo : Optional.ofNullable(handle.prices).orElse(List.of())) {
            var task = this.priceTasks.get(priceInfo.type);
            if (task == null) {
                throw new ShopException("設置錯誤", "無效的 PriceType: " + priceInfo.type);
            }
            priceTasks.put(task, priceInfo);
        }

        priceTasks.forEach(this::validatePriceContent);


        Map<RewardTask<?>, Shop.RewardInfo> rewardTasks = new LinkedHashMap<>();
        for (Shop.RewardInfo rewardInfo : Optional.ofNullable(handle.rewards).orElse(List.of())) {
            var task = this.rewardTasks.get(rewardInfo.type);
            if (task == null) {
                throw new ShopException("設置錯誤", "無效的 RewardType: " + rewardInfo.type);
            }
            rewardTasks.put(task, rewardInfo);
        }

        rewardTasks.forEach(this::validateRewardContent);


        for (PriceTask<?> task : priceTasks.keySet()) {
            var content = priceTasks.get(task);
            future = future.thenCompose(lastResult -> {
                // 前面失敗可立刻返回
                if (!lastResult.isSuccess()) {
                    return CompletableFuture.completedFuture(lastResult);
                }
                return doPurchase(player, task, content.price).thenApply(result -> {
                    if (result.isSuccess()) {
                        rollbackTasks.add(doRollback(player, task, content.price));
                    } else if (content.failedMessage != null) {
                        result.setMessage(content.failedMessage);
                    }
                    return result;
                });
            });
        }

        CompletableFuture<Boolean> priceTask = future.thenCompose(canPurchase -> {

            if (!canPurchase.isSuccess()) {

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', canPurchase.getMessage()));
                return CompletableFuture.allOf(rollbackTasks.toArray(CompletableFuture[]::new)).thenApply(v -> false);

            } else if (!priceTasks.isEmpty()) {
                // TODO custom message
                player.sendMessage("購買成功");
            }

            return CompletableFuture.completedFuture(true);

        });

        if (rewardTasks.isEmpty()) {
            return priceTask.thenAccept(success -> {
            });
        }

        List<CompletableFuture<Void>> rewardFutures = new ArrayList<>();
        for (RewardTask<?> task : rewardTasks.keySet()) {
            var content = rewardTasks.get(task);
            rewardFutures.add(doReward(player, task, content.reward));
        }

        return priceTask.thenCompose(success -> {
            if (success) {
                return CompletableFuture.allOf(rewardFutures.toArray(CompletableFuture[]::new));
            } else {
                return CompletableFuture.completedFuture(null);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private <T> void validateConditions(Condition<T> task, Object info) {
        try {
            var a = (T) info;
        } catch (ClassCastException e) {
            throw new ShopException("設置錯誤", "條件類型 " + task.getName() + " 不接受這個類型: " + info.getClass().getName());
        }
    }

    @SuppressWarnings("unchecked")
    private <T> void validateRewardContent(RewardTask<T> task, Shop.RewardInfo info) {
        try {
            var a = (T) info.reward;
        } catch (ClassCastException e) {
            throw new ShopException("設置錯誤", "獎勵類型 " + task.getName() + " 不接受這個類型: " + info.reward.getClass().getName());
        }
    }

    @SuppressWarnings("unchecked")
    private <T> void validatePriceContent(PriceTask<T> task, Shop.PriceInfo info) {
        try {
            var a = (T) info.price;
        } catch (ClassCastException e) {
            throw new ShopException("設置錯誤", "價格類型 " + task.getName() + " 不接受這個類型: " + info.price.getClass().getName());
        }
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
        T price = (T) content;
        if (task instanceof AsyncPriceTask<T> at) {
            return at.doPurchaseAsync(price, player);
        } else {
            var result = task.doPurchase(price, player);
            return CompletableFuture.completedFuture(result);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> CompletableFuture<Void> doReward(Player player, RewardTask<T> task, Object content) {
        T reward = (T) content;
        if (task instanceof AsyncRewardTask<T> at) {
            return at.giveRewardAsync(reward, player);
        } else {
            task.giveReward(reward, player);
            return CompletableFuture.completedFuture(null);
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
