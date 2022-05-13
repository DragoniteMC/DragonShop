package org.dragonitemc.dragonshop.tasks.rewards;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.api.RewardTask;

import java.util.List;

public class PlayerCommandReward extends RewardTask<List<String>> {

    public PlayerCommandReward() {
        super("player-command");
    }

    @Override
    public void giveReward(List<String> content, Player player) {
        for (String line : content) {
            line = PlaceholderAPI.setPlaceholders(player, line);
            player.chat("/" + line);
        }
    }
}
