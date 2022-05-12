package org.dragonitemc.dragonshop.api;

import org.bukkit.entity.Player;

public abstract class RewardTask<T> {

    private final String name;

    public RewardTask(String name) {
        this.name = name;
    }

    public abstract void giveReward(T content, Player player);

    public String getName() {
        return name;
    }
}
