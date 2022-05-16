package org.dragonitemc.dragonshop.view;

import com.ericlam.mc.eldgui.component.factory.BukkitItemFactory;
import com.ericlam.mc.eldgui.view.UIContext;
import com.ericlam.mc.eldgui.view.UseTemplate;
import com.ericlam.mc.eldgui.view.View;
import org.bukkit.Material;
import org.dragonitemc.dragonshop.component.PlaceholderItemFactory;
import org.dragonitemc.dragonshop.config.GUITemplate;
import org.dragonitemc.dragonshop.model.PageablePlayerShop;

import java.util.List;

@UseTemplate(
        template = "pageable",
        groupResource = GUITemplate.class
)
public class PageableShopView implements View<PageablePlayerShop> {

    @Override
    public void renderView(PageablePlayerShop pps, UIContext context) {

        var player = pps.player;
        var page = pps.page;
        var shopItems = page.getContent();


        BukkitItemFactory detail = context.factory(BukkitItemFactory.class);
        PlaceholderItemFactory button = context.factory(PlaceholderItemFactory.class);
        shopItems.stream().filter(entry -> entry.getValue().slot != -1).forEach(entry -> {

            var id = entry.getKey();
            var itemInfo = entry.getValue();

            context.pattern('A')
                    .component(
                            itemInfo.slot,
                            button.icon(itemInfo.material)
                                    .bind("name", id)
                                    .setPlaceholderPlayer(player)
                                    .number(itemInfo.amount)
                                    .placeholderDisplay(itemInfo.name)
                                    .placeholderLore(itemInfo.lore)
                                    .editItem(f -> {
                                        f.modelData(itemInfo.data);
                                        f.durability(itemInfo.damage);
                                    })
                                    .create()
                    );
        });

        shopItems.stream().filter(entry -> entry.getValue().slot == -1).forEach(entry -> {

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
                                    })
                                    .create()
                    );
        });


        context.pattern('Z')
                .components(
                        detail
                                .icon(Material.PAPER)
                                .number(Math.min(64, Math.max(1, page.getCurrentPage())))
                                .setupByItemFactory(f -> {
                                    f.display(String.format("&e頁數 %d/%d", page.getCurrentPage(), page.getTotalPages()));
                                    f.lore(List.of(String.format("&a共 %d 個物品", page.getTotalElements())));
                                })
                                .create()

                );


    }
}
