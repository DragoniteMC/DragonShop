package org.dragonitemc.dragonshop.tasks.prices;

import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.api.PriceTask;
import org.dragonitemc.dragonshop.api.PurchaseResult;

import java.util.List;

public class HasPermissionPrice extends PriceTask<List<String>> {

    public HasPermissionPrice() {
        super("has-permission");
    }

    @Override
    public PurchaseResult doPurchase(List<String> content, Player player) {
        var result = content.stream().allMatch(player::hasPermission);
        if (result) {
            return PurchaseResult.success();
        } else {
            // TODO custom message
            return PurchaseResult.failed("You don't have permission to buy this item.");
        }
    }
}
