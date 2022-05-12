package org.dragonitemc.dragonshop.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.api.RewardTask;

import java.util.List;

public class CommandReward extends RewardTask<List<String>> {

    public CommandReward() {
        super("command");
    }

    @Override
    public void giveReward(List<String> content, Player player) {
        for (String line : content) {
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), line.replace("{player}", player.getName()));
        }
    }
}
