package org.dragonitemc.dragonshop.tasks.rewards;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.api.RewardTask;

import java.util.List;

public class MessageReward extends RewardTask<List<String>> {

    public MessageReward() {
        super("message");
    }

    @Override
    public void giveReward(List<String> contents, Player player) {
        for (String content : contents) {
            content = PlaceholderAPI.setPlaceholders(player, content);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', content));
        }
    }
}
