package org.dragonitemc.dragonshop.api;

import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public abstract class AsyncPriceTask<T> extends PriceTask<T> {

    public AsyncPriceTask(String name) {
        super(name);
    }

    @Override
    public final PurchaseResult doPurchase(T content, Player player) {
        throw new UnsupportedOperationException("you must use async methods");
    }

    public abstract CompletableFuture<PurchaseResult> doPurchaseAsync(T content, Player player);

}
