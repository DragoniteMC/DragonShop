package org.dragonitemc.dragonshop.model;

import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.config.Shop;

public class PlayerShop {

    public final String title;

    public final Shop shop;

    public final Player player;

    public PlayerShop(Shop shop, Player player) {
        this.title = shop.title;
        this.shop = shop;
        this.player = player;
    }
}
