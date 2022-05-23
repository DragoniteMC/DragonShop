package org.dragonitemc.dragonshop.magic;

import com.ericlam.mc.eldgui.view.View;
import net.bytebuddy.ByteBuddy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ClassGenerator {

    private final Map<String, Class<? extends View<?>>> newClassesMap = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T, V extends View<T>> Class<? extends V> generateViewWithTemplate(String viewType, Class<V> originalView){
        String key = String.format("%s:%s", originalView.getName(), viewType);
        if(newClassesMap.containsKey(key)){
            return (Class<? extends V>) newClassesMap.get(key);
        }
        var newClass = new ByteBuddy()
                .subclass(originalView)
                .annotateType(new DynamicTemplate(viewType))
                .make()
                .load(ClassGenerator.class.getClassLoader())
                .getLoaded();
        newClassesMap.put(key, newClass);
        return newClass;
    }
}
