package org.dragonitemc.dragonshop.magic;

import com.ericlam.mc.eldgui.InventoryTemplate;
import com.ericlam.mc.eldgui.view.UseTemplate;

import java.lang.annotation.Annotation;

public final class DynamicTemplate implements UseTemplate {

    private final String template;

    public DynamicTemplate(String template) {
        this.template = template;
    }

    @Override
    public String template() {
        return template;
    }

    @Override
    public Class<? extends InventoryTemplate> groupResource() {
        return InventoryTemplate.class;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return UseTemplate.class;
    }
}
