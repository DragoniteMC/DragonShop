package org.dragonitemc.dragonshop.tasks;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.api.RewardTask;

public class MessageReward extends RewardTask<String> {

    public MessageReward() {
        super("message");
    }

    @Override
    public void giveReward(String content, Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', content));
    }
}
