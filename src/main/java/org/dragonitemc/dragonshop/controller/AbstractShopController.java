package org.dragonitemc.dragonshop.controller;

import com.ericlam.mc.eld.annotations.InjectPool;
import com.ericlam.mc.eld.configurations.GroupConfig;
import com.ericlam.mc.eldgui.UISession;
import com.ericlam.mc.eldgui.controller.ItemAttribute;
import com.ericlam.mc.eldgui.event.ClickMapping;
import com.ericlam.mc.eldgui.view.AnyView;
import com.ericlam.mc.eldgui.view.BukkitRedirectView;
import com.ericlam.mc.eldgui.view.BukkitView;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.dragonitemc.dragonshop.ShopException;
import org.dragonitemc.dragonshop.api.ShopTaskService;
import org.dragonitemc.dragonshop.config.Shop;
import org.dragonitemc.dragonshop.magic.ClassGenerator;
import org.dragonitemc.dragonshop.services.ShopTaskManager;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractShopController {

    @Inject
    protected ShopTaskService shopTaskService;

    @InjectPool
    protected GroupConfig<Shop> shopConfig;


    @Inject
    protected ClassGenerator classGenerator;

    public BukkitView<?, ?> index(Player player, UISession session) {
        Shop shop = session.getAttribute("shop");
        if (shop == null) {
            throw new ShopException("商店無效!", "請重新打開界面試試。");
        }
        return indexView(player, session, shop);
    }

    public abstract BukkitView<?, ?> indexView(Player player, UISession session, Shop shop);


    @ClickMapping(pattern = 'A', view = AnyView.class)
    public CompletableFuture<BukkitView<?, ?>> onClick(Player player, @Nullable @ItemAttribute("name") String name, InventoryClickEvent e, UISession session) {
        if (name == null) return null;
        var clickType = e.getClick();
        Shop shop = session.getAttribute("shop");
        if (shop == null) {
            throw new ShopException("商店無效!", "請重新打開界面試試。");
        }
        var itemInfo = shop.shopItems.get(name);
        if (itemInfo == null) {
            throw new ShopException("無效的物品", "物品 ID 不存在");
        }

        var taskManager = (ShopTaskManager) shopTaskService;
        if (itemInfo.toShop != null && !itemInfo.toShop.isBlank()) {
            var toShop = shopConfig.findById(itemInfo.toShop).orElseThrow(() -> new ShopException("找不到商店", "商店 " + itemInfo.toShop + " 不存在"));
            session.setAttribute("shop", toShop);
            // same gui type
            if (toShop.guiType.equals(shop.guiType)) {
                return CompletableFuture.completedFuture(index(player, session));
            } else {
                return CompletableFuture.completedFuture(new BukkitRedirectView(String.format("dshop.%s", toShop.guiType)));
            }
        }
        var handle = itemInfo.handles.get(clickType);
        if (handle == null) {
            return null;
        }
        return taskManager.handleTask(player, handle).thenApply(v -> index(player, session));
    }

}
