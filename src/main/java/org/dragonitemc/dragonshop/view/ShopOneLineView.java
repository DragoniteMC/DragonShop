package org.dragonitemc.dragonshop.view;

import com.ericlam.mc.eldgui.component.factory.ButtonFactory;
import com.ericlam.mc.eldgui.view.UIContext;
import com.ericlam.mc.eldgui.view.UseTemplate;
import com.ericlam.mc.eldgui.view.View;
import org.dragonitemc.dragonshop.config.GUITemplate;
import org.dragonitemc.dragonshop.config.Shop;

@UseTemplate(
        template = "one-line",
        groupResource = GUITemplate.class
)
public class ShopOneLineView implements View<Shop> {

    @Override
    public void renderView(Shop shop, UIContext context) {
        ButtonFactory button = context.factory(ButtonFactory.class);
        shop.shopItems.entrySet().stream().filter(entry -> entry.getValue().slot != -1).forEach(entry -> {
            context.pattern('A')
                    .component(
                            entry.getValue().slot,
                            button.icon(entry.getValue().material)
                                    .bind("name", entry.getValue().name)
                                    .title(entry.getValue().name)
                                    .lore(entry.getValue().lore.toArray(String[]::new))
                                    .create()
                    );
        });

        shop.shopItems.entrySet().stream().filter(entry -> entry.getValue().slot == -1).forEach(entry -> {
            context.pattern('A')
                    .components(
                            button.icon(entry.getValue().material)
                                    .bind("name", entry.getValue().name)
                                    .title(entry.getValue().name)
                                    .lore(entry.getValue().lore.toArray(String[]::new))
                                    .create()
                    );
        });
    }

}
