package org.dragonitemc.dragonshop.api;

import com.ericlam.mc.eld.services.ScheduleService;
import org.bukkit.entity.Player;

public abstract class AsyncRewardTask<T> extends RewardTask<T> {

    public AsyncRewardTask(String name) {
        super(name);
    }

    @Override
    public final void giveReward(T content, Player player) {
        throw new UnsupportedOperationException("you must use async methods");
    }


    public abstract ScheduleService.BukkitPromise<Void> giveRewardAsync(T content, Player player);

}
