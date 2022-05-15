package org.dragonitemc.dragonshop.tasks.rewards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.DragonShop;
import org.dragonitemc.dragonshop.api.RewardTask;

public class CloseReward extends RewardTask<Object> {

    public CloseReward() {
        super("close");
    }

    @Override
    public void giveReward(Object content, Player player) {
        Bukkit.getScheduler().runTask(DragonShop.getProvidingPlugin(DragonShop.class), () -> player.closeInventory());
    }
}
