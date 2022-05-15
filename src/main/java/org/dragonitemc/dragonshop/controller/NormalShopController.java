package org.dragonitemc.dragonshop.controller;

import com.ericlam.mc.eldgui.UISession;
import com.ericlam.mc.eldgui.controller.AsyncLoadingView;
import com.ericlam.mc.eldgui.controller.UIController;
import com.ericlam.mc.eldgui.view.BukkitView;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.config.Shop;
import org.dragonitemc.dragonshop.model.PlayerShop;
import org.dragonitemc.dragonshop.view.AsyncShopView;
import org.dragonitemc.dragonshop.view.NormalShopView;

import java.util.HashMap;
import java.util.Map;

@UIController("dshop.normal")
@AsyncLoadingView(AsyncShopView.class)
public class NormalShopController extends AbstractShopController {

    @Override
    public BukkitView<?, ?> indexView(Player player, UISession session, Shop shop) {
        var disabled = shopTaskService.getDisabledItemIds(player,
                shop.shopItems.entrySet()
                        .stream()
                        .filter(e -> e.getValue().conditions != null && !e.getValue().conditions.isEmpty())
                        .map(e -> Map.entry(e.getKey(), e.getValue().conditions)));
        var map = new HashMap<>(shop.shopItems);
        map.keySet().removeAll(disabled);
        return new BukkitView<>(NormalShopView.class, new PlayerShop(shop, player, map));
    }

}
