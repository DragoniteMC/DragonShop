package org.dragonitemc.dragonshop.config;

import com.ericlam.mc.eld.annotations.GroupResource;
import com.ericlam.mc.eldgui.InventoryTemplate;

@GroupResource(
        folder = "gui",
        preloads = {"main", "one-line", "two-line"}
)
public class GUITemplate extends InventoryTemplate {
}
