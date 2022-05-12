package org.dragonitemc.dragonshop.api;

import com.ericlam.mc.eldgui.view.View;

public interface ShopService {

    void addView(String name, Class<? extends View<?>> view);

    void removeView(String name);

    Class<? extends View<Object>> getView(String name);

}
