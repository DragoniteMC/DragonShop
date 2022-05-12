package org.dragonitemc.dragonshop.api;

import com.ericlam.mc.eld.services.ScheduleService;
import org.bukkit.entity.Player;

public abstract class AsyncPriceTask<T> extends PriceTask<T> {

    public AsyncPriceTask(String name) {
        super(name);
    }

    @Override
    public final PurchaseResult doPurchase(T content, Player player) {
        throw new UnsupportedOperationException("you must use async methods");
    }

    public abstract ScheduleService.BukkitPromise<PurchaseResult> doPurchaseAsync(T content, Player player);

}
