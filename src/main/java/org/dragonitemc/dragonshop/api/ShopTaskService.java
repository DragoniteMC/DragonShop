package org.dragonitemc.dragonshop.api;

import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.config.Shop;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public interface ShopTaskService {

    void addRewardTask(RewardTask<?> rewardTask);

    void addPriceTask(PriceTask<?> priceTask);

    void addCondition(Condition<?> condition);

    boolean isPassCondition(Player player, Shop.ShopItemInfo info);

    Set<String> getDisabledItemIds(Player player, Stream<Map.Entry<String, List<Shop.ConditionInfo>>> items);
}
