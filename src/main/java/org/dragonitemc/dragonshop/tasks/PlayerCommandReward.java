package org.dragonitemc.dragonshop.tasks;

import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.api.RewardTask;

import java.util.List;

public class PlayerCommandReward extends RewardTask<List<String>> {

    public PlayerCommandReward() {
        super("player-command");
    }

    @Override
    public void giveReward(List<String> content, Player player) {
        content.forEach(player::performCommand);
    }
}
