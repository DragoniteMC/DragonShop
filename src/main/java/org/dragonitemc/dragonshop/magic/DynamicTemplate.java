package org.dragonitemc.dragonshop.magic;

import com.ericlam.mc.eldgui.InventoryTemplate;
import com.ericlam.mc.eldgui.view.UseTemplate;
import org.dragonitemc.dragonshop.config.GUITemplate;

import java.lang.annotation.Annotation;

public record DynamicTemplate(String template) implements UseTemplate {

    @Override
    public Class<? extends InventoryTemplate> groupResource() {
        return GUITemplate.class;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return UseTemplate.class;
    }
}
