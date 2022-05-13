package org.dragonitemc.dragonshop.tasks.rewards;

import com.dragonite.mc.dnmc.core.main.DragoniteMC;
import com.dragonite.mc.dnmc.core.managers.BungeeManager;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.api.RewardTask;

public class BungeeServerReward extends RewardTask<String> {

    public BungeeServerReward() {
        super("bungee-server");
    }

    @Override
    public void giveReward(String server, Player player) {
        BungeeManager bungeeManager = DragoniteMC.getAPI().getBungeeManager();
        bungeeManager.sendPlayer(player, server);
    }
}
