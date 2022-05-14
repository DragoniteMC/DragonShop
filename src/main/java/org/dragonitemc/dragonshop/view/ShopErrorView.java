package org.dragonitemc.dragonshop.view;

import com.ericlam.mc.eldgui.component.factory.ButtonFactory;
import com.ericlam.mc.eldgui.view.UIContext;
import com.ericlam.mc.eldgui.view.View;
import com.ericlam.mc.eldgui.view.ViewDescriptor;
import org.bukkit.Material;
import org.dragonitemc.dragonshop.ShopException;


@ViewDescriptor(
        name = "商店錯誤",
        rows = 1,
        patterns = "ZZZZXZZZZ",
        cancelMove = { 'Z', 'X'}
)
public class ShopErrorView implements View<ShopException> {


    @Override
    public void renderView(ShopException e, UIContext uiContext) {
        ButtonFactory btn = uiContext.factory(ButtonFactory.class);
        uiContext.pattern('Z').fill(btn.icon(Material.BLACK_STAINED_GLASS_PANE).create());

        uiContext.pattern('A')
                .components(
                    btn.icon(Material.BARRIER)
                            .title(e.getTitle() == null ? "錯誤" : e.getTitle())
                            .lore(e.getMessage() == null ? "null" : e.getMessage())
                            .create()
                );

    }
}
