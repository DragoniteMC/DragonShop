package org.dragonitemc.dragonshop.services;

import com.ericlam.mc.eld.annotations.InjectPool;
import com.ericlam.mc.eld.configurations.GroupConfig;
import com.ericlam.mc.eldgui.InventoryService;
import com.ericlam.mc.eldgui.UINotFoundException;
import com.ericlam.mc.eldgui.UISession;
import com.ericlam.mc.eldgui.view.View;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.api.ShopService;
import org.dragonitemc.dragonshop.config.Shop;

import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

public class ShopManager implements ShopService {

    @Inject
    private InventoryService inventoryService;

    @InjectPool
    private GroupConfig<Shop> shopGroupConfig;

    @Override
    public void openShop(Player player, String shop) throws UINotFoundException {

        if (shopGroupConfig == null) {
            player.sendMessage("group config is null");
        }

        var shopOpt = shopGroupConfig.findById(shop);
        if (shopOpt.isEmpty()) throw new UINotFoundException("Shop "+shop+" not found");
        openShop(player, shopOpt.get());
    }

    @Override
    public void openShop(Player player, Shop shop) throws UINotFoundException {
        var ui = inventoryService.getUIDispatcher(String.format("dshop.%s", shop.guiType));
        ui.openFor(player, session -> session.setAttribute("shop", shop));
    }

    @Override
    public void openShop(Player player, Shop shop, Consumer<UISession> sessionConsumer) throws UINotFoundException {
        var ui = inventoryService.getUIDispatcher(String.format("dshop.%s", shop.guiType));
        ui.openFor(player, session -> {
            session.setAttribute("shop", shop);
            sessionConsumer.accept(session);
        });
    }
}
