package org.dragonitemc.dragonshop.config;

import com.ericlam.mc.eld.annotations.GroupResource;
import com.ericlam.mc.eldgui.InventoryTemplate;

@GroupResource(
        folder = "gui",
        preloads = {"pageable", "normal", "async-loading"}
)
public class GUITemplate extends InventoryTemplate {
}
