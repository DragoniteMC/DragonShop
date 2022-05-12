package org.dragonitemc.dragonshop.services;

import com.ericlam.mc.eldgui.view.View;
import org.dragonitemc.dragonshop.api.ShopService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShopManager implements ShopService {

    private Map<String, Class<? extends View<Object>>> views = new ConcurrentHashMap<>();

    @Override
    public void addView(String name, Class<? extends View<?>> view) {
        views.put(name, (Class<? extends View<Object>>)view);
    }

    @Override
    public void removeView(String name) {
        views.remove(name);
    }

    @Override
    public Class<? extends View<Object>> getView(String name) {
        return views.get(name);
    }

}
