package org.dragonitemc.dragonshop.view;

import com.ericlam.mc.eldgui.view.LoadingView;
import com.ericlam.mc.eldgui.view.UIContext;
import com.ericlam.mc.eldgui.view.UseTemplate;
import org.dragonitemc.dragonshop.config.GUITemplate;


@UseTemplate(
        template = "async-loading",
        groupResource = GUITemplate.class
)
public class AsyncShopView implements LoadingView {

    @Override
    public void renderView(Void unused, UIContext uiContext) {
    }
}
