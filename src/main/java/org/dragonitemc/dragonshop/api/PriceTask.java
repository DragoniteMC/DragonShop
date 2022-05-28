package org.dragonitemc.dragonshop.api;

import org.bukkit.entity.Player;

public abstract class PriceTask<T> implements ShopTask {

    private final String name;

    public abstract PurchaseResult doPurchase(T content, Player player);
    public abstract void doRollBack(T content, Player player);

    public PriceTask(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
