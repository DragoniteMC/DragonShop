package org.dragonitemc.dragonshop.controller;

import com.ericlam.mc.eld.annotations.InjectPool;
import com.ericlam.mc.eld.configurations.GroupConfig;
import com.ericlam.mc.eld.services.ScheduleService;
import com.ericlam.mc.eldgui.UISession;
import com.ericlam.mc.eldgui.controller.ItemAttribute;
import com.ericlam.mc.eldgui.event.ClickMapping;
import com.ericlam.mc.eldgui.view.AnyView;
import com.ericlam.mc.eldgui.view.BukkitView;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.dragonitemc.dragonshop.ShopException;
import org.dragonitemc.dragonshop.api.ShopTaskService;
import org.dragonitemc.dragonshop.config.Shop;
import org.dragonitemc.dragonshop.services.ShopTaskManager;

import javax.inject.Inject;

public abstract class AbstractShopController {

    @Inject
    protected ShopTaskService shopTaskService;


    @InjectPool
    protected GroupConfig<Shop> shopConfig;

    public BukkitView<?, ?> index(Player player, UISession session) {
        Shop shop = session.getAttribute("shop");
        if (shop == null) {
            throw new ShopException("商店無效!", "請重新打開界面試試。");
        }
        return indexView(player, session, shop);
    }

    public abstract BukkitView<?, ?> indexView(Player player, UISession session, Shop shop);


    @ClickMapping(pattern = 'A', view = AnyView.class)
    public ScheduleService.BukkitPromise<BukkitView<?, ?>> onClick(Player player, @ItemAttribute("name") String name, InventoryClickEvent e, UISession session) {
        var clickType = e.getClick();
        Shop shop = session.getAttribute("shop");
        if (shop == null) {
            throw new ShopException("商店無效!", "請重新打開界面試試。");
        }
        var itemInfo = shop.shopItems.get(name);
        if (itemInfo == null) {
            throw new ShopException("無效的物品", "物品 ID 不存在");
        }

        var taskManager = (ShopTaskManager)shopTaskService;
        if (itemInfo.toShop != null) {
            shop = shopConfig.findById(itemInfo.toShop).orElseThrow(() -> new ShopException("找不到商店", "商店 "+itemInfo.toShop+" 不存在"));
            session.setAttribute("shop", shop);
            return taskManager.handleTask(player, null, null).thenApplySync(v -> index(player, session));
        }
        var reward = itemInfo.rewards.get(clickType);
        var price = itemInfo.prices.get(clickType);
        return taskManager.handleTask(player, reward, price).thenApplySync(v -> index(player, session));
    }

}
