package org.dragonitemc.dragonshop.tasks.rewards;

import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.api.RewardTask;

public class CloseReward extends RewardTask<Object> {

    public CloseReward() {
        super("close");
    }

    @Override
    public void giveReward(Object content, Player player) {
        player.closeInventory();
    }
}
