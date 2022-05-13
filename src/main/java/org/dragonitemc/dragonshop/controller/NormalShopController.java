package org.dragonitemc.dragonshop.controller;

import com.ericlam.mc.eldgui.UISession;
import com.ericlam.mc.eldgui.controller.UIController;
import com.ericlam.mc.eldgui.view.BukkitView;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.config.Shop;
import org.dragonitemc.dragonshop.model.PlayerShop;
import org.dragonitemc.dragonshop.view.NormalShopView;

@UIController("dshop.normal")
public class NormalShopController extends AbstractShopController {

    @Override
    public BukkitView<?, ?> indexView(Player player, UISession session, Shop shop) {
        return new BukkitView<>(NormalShopView.class, new PlayerShop(shop, player));
    }

}
