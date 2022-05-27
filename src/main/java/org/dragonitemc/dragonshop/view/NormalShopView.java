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
public class NormalShopView implements View<PlayerShop> {

    @Override
    public void renderView(PlayerShop playerShop, UIContext context) {

        var player = playerShop.player;

        PlaceholderItemFactory button = context.factory(PlaceholderItemFactory.class);
        playerShop.shopItems.entrySet().stream().filter(entry -> entry.getValue().slot != -1).forEach(entry -> {

            var id = entry.getKey();
            var itemInfo = entry.getValue();


            var item = button.icon(itemInfo.material)
                    .bind("name", id)
                    .setPlaceholderPlayer(player)
                    .number(itemInfo.amount)
                    .placeholderDisplay(itemInfo.name)
                    .placeholderLore(itemInfo.lore)
                    .editItem(f -> {
                        f.modelData(itemInfo.data);
                        f.durability(itemInfo.damage);
                        f.unbreakable(itemInfo.damage > 0);
                    })
                    .create();


            if (itemInfo.slots != null){

                for(int slot : itemInfo.slots){
                    context.pattern('A').component(slot, item);
                }

            }else{

                context.pattern('A').component(itemInfo.slot, item);

            }
        });

        playerShop.shopItems.entrySet().stream().filter(entry -> entry.getValue().slot == -1).forEach(entry -> {

            var id = entry.getKey();
            var itemInfo = entry.getValue();

            context.pattern('A')
                    .components(
                            button.icon(itemInfo.material)
                                    .bind("name", id)
                                    .number(itemInfo.amount)
                                    .setPlaceholderPlayer(player)
                                    .placeholderDisplay(itemInfo.name)
                                    .placeholderLore(itemInfo.lore)
                                    .editItem(f -> {
                                        f.modelData(itemInfo.data);
                                        f.durability(itemInfo.damage);
                                        f.unbreakable(itemInfo.damage > 0);
                                    })
                                    .create()
                    );
        });
    }
}
