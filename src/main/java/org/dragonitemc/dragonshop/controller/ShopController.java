package org.dragonitemc.dragonshop.controller;

import com.ericlam.mc.eld.services.ScheduleService;
import com.ericlam.mc.eldgui.UISession;
import com.ericlam.mc.eldgui.controller.ItemAttribute;
import com.ericlam.mc.eldgui.controller.UIController;
import com.ericlam.mc.eldgui.event.ClickMapping;
import com.ericlam.mc.eldgui.event.RequestMapping;
import com.ericlam.mc.eldgui.view.AnyView;
import com.ericlam.mc.eldgui.view.BukkitRedirectView;
import com.ericlam.mc.eldgui.view.BukkitView;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.dragonitemc.dragonshop.ShopException;
import org.dragonitemc.dragonshop.api.ShopService;
import org.dragonitemc.dragonshop.api.ShopTaskService;
import org.dragonitemc.dragonshop.config.Shop;
import org.dragonitemc.dragonshop.services.ShopTaskManager;
import org.dragonitemc.dragonshop.view.ShopOneLineView;
import org.dragonitemc.dragonshop.view.ShopTwoLineView;

import javax.inject.Inject;

@UIController("shop")
public class ShopController {

    @Inject
    private ShopService shopService;

    @Inject
    private ShopTaskService taskService;

    public BukkitView<?, ?> index(UISession session){
        Shop shop = session.getAttribute("shop");
        if (shop == null){
            throw new ShopException("商店無效!", "請重新打開界面試試。");
        }
        var view = shopService.getView(shop.viewType);
        return new BukkitView<>(view, shop);
    }

    @ClickMapping(pattern = 'A', view = AnyView.class)
    public ScheduleService.BukkitPromise<BukkitView<?, ?>> onClick(Player player, @ItemAttribute("name") String name, InventoryClickEvent e, UISession session){
        var clickType = e.getClick();
        Shop shop = session.getAttribute("shop");
        if (shop == null){
            throw new ShopException("商店無效!", "請重新打開界面試試。");
        }
        var itemInfo = shop.shopItems.get(name);
        if (itemInfo == null){
            throw new ShopException("無效的物品", "物品 ID 不存在");
        }
        var reward = itemInfo.rewards.get(clickType);
        var price = itemInfo.prices.get(clickType);

        return ((ShopTaskManager)taskService).handleTask(player, reward, price).thenApplySync(v -> index(session));
    }



}
