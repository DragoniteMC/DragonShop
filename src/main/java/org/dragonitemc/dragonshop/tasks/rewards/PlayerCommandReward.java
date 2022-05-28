package org.dragonitemc.dragonshop.tasks.rewards;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.DragonShop;
import org.dragonitemc.dragonshop.api.RewardTask;
import org.dragonitemc.dragonshop.model.DelayedCommands;

import java.util.List;

public class PlayerCommandReward extends RewardTask<DelayedCommands> {

    public PlayerCommandReward() {
        super("player-command");
    }

    @Override
    public void giveReward(DelayedCommands content, Player player) {
        if (content.delay <= 0) {
            runCommands(player, content.commands);
        } else {
            Bukkit.getScheduler()
                    .runTaskLater(DragonShop.getProvidingPlugin(DragonShop.class),
                            () -> runCommands(player, content.commands)
                            , content.delay);
        }
    }

    private void runCommands(Player player, List<String> commands) {
        for (String line : commands) {
            line = PlaceholderAPI.setPlaceholders(player, line);
            player.chat("/" + line);
        }
    }
}
