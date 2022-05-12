package org.dragonitemc.dragonshop;

import com.ericlam.mc.eld.ELDLifeCycle;
import org.bukkit.plugin.java.JavaPlugin;
import org.dragonitemc.dragonshop.api.ShopService;
import org.dragonitemc.dragonshop.view.ShopOneLineView;
import org.dragonitemc.dragonshop.view.ShopTwoLineView;

import javax.inject.Inject;

public class DragonShopLifeCycle implements ELDLifeCycle {

    @Inject
    private ShopService shopService;

    @Override
    public void onEnable(JavaPlugin plugin) {
        shopService.addView("one-line", ShopOneLineView.class);
        shopService.addView("two-line", ShopTwoLineView.class);
    }

    @Override
    public void onDisable(JavaPlugin plugin) {

    }

}
