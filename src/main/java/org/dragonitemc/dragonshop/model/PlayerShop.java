package org.dragonitemc.dragonshop.model;

import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.config.Shop;

import java.util.Map;
import java.util.Set;

public class PlayerShop {

    public final String title;
    public final Map<String, Shop.ShopItemInfo> shopItems;

    public final Player player;

    public PlayerShop(Shop shop, Player player, Map<String, Shop.ShopItemInfo> shopItems) {
        this.title = shop.title;
        this.shopItems = shopItems;
        this.player = player;
    }
}
