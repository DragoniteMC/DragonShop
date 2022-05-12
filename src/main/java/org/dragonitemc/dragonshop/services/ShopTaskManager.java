package org.dragonitemc.dragonshop.services;

import com.ericlam.mc.eld.services.ScheduleService;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.DragonShop;
import org.dragonitemc.dragonshop.ShopException;
import org.dragonitemc.dragonshop.api.*;
import org.dragonitemc.dragonshop.config.Shop;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShopTaskManager implements ShopTaskService {

    @Inject
    private DragonShop plugin;

    @Inject
    private ScheduleService scheduleService;

    private final Map<String, RewardTask<?>> rewardTasks = new ConcurrentHashMap<>();
    private final Map<String, PriceTask<?>> priceTasks = new ConcurrentHashMap<>();


    public ScheduleService.BukkitPromise<Void> handleTask(
            Player player,
            @Nullable Shop.RewardInfo rewardInfo,
            @Nullable Shop.PriceInfo priceInfo
    ) {

        ScheduleService.BukkitPromise<Void> initTask = scheduleService.runAsync(plugin, () -> {});

        if (rewardInfo == null && priceInfo == null){
            return initTask;
        }

        ScheduleService.BukkitPromise<PurchaseResult> purchaseTask;

        if (priceInfo == null){

            purchaseTask = initTask.thenApplySync(v -> PurchaseResult.success());

        } else {

            var priceTask = priceTasks.get(priceInfo.priceType);
            if (priceTask == null){
                throw new ShopException("設置錯誤", "無效的 PriceType: " + priceInfo.priceType);
            }

            purchaseTask = doPurchase(priceTask, priceInfo.price, player, initTask);

        }

        var resultTask = purchaseTask.thenRunSync(purchaseResult -> {
            if (purchaseResult.isSuccess()){
                // 之後再改
                player.sendMessage("Purchase Successful");
            }else{
                player.sendMessage("Purchase Failed: " + purchaseResult.getMessage());
            }
        });

        if (rewardInfo == null){
            return resultTask;
        }

        var rewardTask = rewardTasks.get(rewardInfo.rewardType);

        if (rewardTask == null){
            throw new ShopException("設置錯誤", "無效的 RewardType: " + rewardInfo.rewardType);
        }


        return doReward(rewardTask, rewardInfo.reward, player, resultTask);
    }



    @SuppressWarnings("unchecked")
    private <T> ScheduleService.BukkitPromise<Void> doReward(RewardTask<T> task, Object content, Player player, ScheduleService.BukkitPromise<Void> initTask){

        T reward;
        try {
            reward = (T)content;
        }catch (ClassCastException e){
            throw new ShopException("設置錯誤", "獎勵類型 "+ task.getName()+" 不接受這個類型: " + content.getClass().getName());
        }

        if (task instanceof AsyncRewardTask<T> asyncRewardTask){

            return initTask.thenRunAsync(v -> {
                try {
                    asyncRewardTask.giveRewardAsync(reward, player).block();
                }catch (Throwable e){
                    e.printStackTrace();
                }
            });

        } else {

            return initTask.thenRunSync(v -> task.giveReward(reward, player));
        }

    }
    @SuppressWarnings("unchecked")
    private <T> ScheduleService.BukkitPromise<PurchaseResult> doPurchase(PriceTask<T> task, Object content, Player player, ScheduleService.BukkitPromise<Void> initTask){

        T price;
        try {
            price = (T)content;
        }catch (ClassCastException e){
            throw new ShopException("設置錯誤", "付費類型 "+ task.getName()+" 不接受這個類型: " + content.getClass().getName());
        }

        if (task instanceof AsyncPriceTask<T> asyncPriceTask){

            return initTask.thenApplyAsync(v -> {
                try {
                    return asyncPriceTask.doPurchaseAsync(price, player).block();
                }catch (Throwable e){
                    e.printStackTrace();
                    return PurchaseResult.failed(e.getMessage());
                }
            });

        } else {

            return initTask.thenApplySync(v -> task.doPurchase(price, player));
        }

    }

    @Override
    public void addRewardTask(RewardTask<?> rewardTask) {
        this.rewardTasks.put(rewardTask.getName(), rewardTask);
    }

    @Override
    public void addPriceTask(PriceTask<?> priceTask) {
        this.priceTasks.put(priceTask.getName(), priceTask);
    }
}
