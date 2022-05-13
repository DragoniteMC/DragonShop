package org.dragonitemc.dragonshop.api;

import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public abstract class AsyncRewardTask<T> extends RewardTask<T> {

    public AsyncRewardTask(String name) {
        super(name);
    }

    @Override
    public final void giveReward(T content, Player player) {
        throw new UnsupportedOperationException("you must use async methods");
    }


    public abstract CompletableFuture<Void> giveRewardAsync(T content, Player player);

}
