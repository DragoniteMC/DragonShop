package org.dragonite.mc.testshop;

import com.ericlam.mc.eldgui.InventoryTemplate;
import com.ericlam.mc.eldgui.view.UIContext;
import com.ericlam.mc.eldgui.view.UseTemplate;
import com.ericlam.mc.eldgui.view.View;
import com.fasterxml.jackson.core.type.TypeReference;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDescription;
import org.dragonitemc.dragonshop.magic.DynamicTemplate;

public class TestByteBuddy {

    public static void main(String[] args) {

        var original = TestView.class;
        System.out.println(original.getAnnotation(UseTemplate.class));


        var viewVoid = TypeDescription.Generic.Builder.of(new TypeReference<View<Void>>(){}.getType()).build();
        var after = new ByteBuddy()
                .subclass(original)
                .annotateType(new DynamicTemplate("test-456"))
                .make()
                .load(original.getClassLoader())
                .getLoaded();

        System.out.println(after.getAnnotation(UseTemplate.class));
    }



    @UseTemplate(
            template = "test-123",
            groupResource = InventoryTemplate.class
    )
    public static class TestView implements View<Void>{

        @Override
        public void renderView(Void unused, UIContext uiContext) {
        }
    }
}
