package org.dragonitemc.dragonshop.tasks.rewards;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.PermissionNode;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.api.RewardTask;

import java.util.List;

public class PermissionReward extends RewardTask<List<String>> {

    public PermissionReward() {
        super("permission");
    }

    @Override
    public void giveReward(List<String> content, Player player) {
        var lp = LuckPermsProvider.get();
        User user = lp.getUserManager().getUser(player.getUniqueId());
        if (user == null) {
            player.sendMessage("unknown user");
            return;
        }
        var d = user.data();
        content.forEach(s -> d.add(PermissionNode.builder().permission(s).build()));
        lp.getUserManager().saveUser(user);
        player.sendMessage("獎勵了權限: " + String.join(", ", content));
    }
}
