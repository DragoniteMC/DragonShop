package org.dragonitemc.dragonshop.view;

import com.ericlam.mc.eldgui.view.UIContext;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.component.PlaceholderItemFactory;
import org.dragonitemc.dragonshop.config.Shop;

import java.util.Collection;
import java.util.Map;

public abstract class ShopView {


    protected void placeItems(Player player, UIContext context, PlaceholderItemFactory button, Collection<Map.Entry<String, Shop.ShopItemInfo>> shopItems) {

        shopItems.stream().filter(entry -> entry.getValue().slot != -1 || entry.getValue().slots != null).forEach(entry -> {

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


            if (itemInfo.slots != null) {

                for (int slot : itemInfo.slots) {
                    context.pattern('A').component(slot, item);
                }

            } else {

                context.pattern('A').component(itemInfo.slot, item);

            }
        });

        shopItems.stream().filter(entry -> entry.getValue().slot == -1 && entry.getValue().slots == null).forEach(entry -> {

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
