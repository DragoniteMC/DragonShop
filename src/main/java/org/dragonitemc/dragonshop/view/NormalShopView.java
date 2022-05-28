package org.dragonitemc.dragonshop.view;

import com.ericlam.mc.eldgui.view.UIContext;
import com.ericlam.mc.eldgui.view.UseTemplate;
import com.ericlam.mc.eldgui.view.View;
import org.dragonitemc.dragonshop.component.PlaceholderItemFactory;
import org.dragonitemc.dragonshop.config.GUITemplate;
import org.dragonitemc.dragonshop.model.PlayerShop;

@UseTemplate(
        template = "normal",
        groupResource = GUITemplate.class
)
public class NormalShopView extends ShopView implements View<PlayerShop> {

    @Override
    public void renderView(PlayerShop playerShop, UIContext context) {

        var player = playerShop.player;

        PlaceholderItemFactory button = context.factory(PlaceholderItemFactory.class);
        placeItems(player, context, button, playerShop.shopItems.entrySet());
    }
}
