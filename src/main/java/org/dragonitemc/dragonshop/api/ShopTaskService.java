package org.dragonitemc.dragonshop.api;

public interface ShopTaskService {

    void addRewardTask(RewardTask<?> rewardTask);

    void addPriceTask(PriceTask<?> priceTask);

}
