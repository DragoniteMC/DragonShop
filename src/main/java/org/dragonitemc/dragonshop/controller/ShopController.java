package org.dragonitemc.dragonshop.controller;

import com.ericlam.mc.eldgui.UISession;
import com.ericlam.mc.eldgui.controller.ItemAttribute;
import com.ericlam.mc.eldgui.controller.UIController;
import com.ericlam.mc.eldgui.event.ClickMapping;
import com.ericlam.mc.eldgui.event.RequestMapping;
import com.ericlam.mc.eldgui.view.BukkitView;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.dragonitemc.dragonshop.api.ShopService;
import org.dragonitemc.dragonshop.config.Shop;
import org.dragonitemc.dragonshop.view.ShopOneLineView;
import org.dragonitemc.dragonshop.view.ShopTwoLineView;

import javax.inject.Inject;

@UIController("shop")
public class ShopController {

    @Inject
    private ShopService shopService;

    public BukkitView<?, ?> index(UISession session){
        Shop shop = session.getAttribute("shop");
        var view = shopService.getView(shop.viewType);
        return new BukkitView<>(view, shop);
    }

    @ClickMapping(pattern = 'A', view = ShopOneLineView.class)
    public void onClickOne(Player player, @ItemAttribute("name") String name, InventoryClickEvent e){

    }

    @ClickMapping(pattern = 'A', view = ShopTwoLineView.class)
    public void onClickTwo(Player player, @ItemAttribute("name") String name, InventoryClickEvent e) {

    }

}
