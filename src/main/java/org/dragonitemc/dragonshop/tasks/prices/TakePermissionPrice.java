package org.dragonitemc.dragonshop.tasks.prices;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.PermissionNode;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.api.PriceTask;
import org.dragonitemc.dragonshop.api.PurchaseResult;

import java.util.List;

public class TakePermissionPrice extends PriceTask<List<String>> {

    public TakePermissionPrice() {
        super("take-permission");
    }

    @Override
    public PurchaseResult doPurchase(List<String> content, Player player) {
        if (!content.stream().allMatch(player::hasPermission)) return PurchaseResult.failed("player permission is not satisfied");
        var lp = LuckPermsProvider.get();
        User user = lp.getUserManager().getUser(player.getUniqueId());
        if (user == null) {
            return PurchaseResult.failed("unknown user");
        }
        var d = user.data();
        content.forEach(p -> d.remove(PermissionNode.builder(p).build()));
        lp.getUserManager().saveUser(user);
        return PurchaseResult.success();
    }
}
