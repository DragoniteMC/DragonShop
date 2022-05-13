package org.dragonitemc.dragonshop.model;

import com.ericlam.mc.eld.configurations.Page;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.config.Shop;

import java.util.Map;

public class PageablePlayerShop {

    public final String title;
    public final Page<Map.Entry<String, Shop.ShopItemInfo>> page;
    public final Player player;

    public PageablePlayerShop(String title, Page<Map.Entry<String, Shop.ShopItemInfo>> page, Player player) {
        this.title = title;
        this.page = page;
        this.player = player;
    }
}
