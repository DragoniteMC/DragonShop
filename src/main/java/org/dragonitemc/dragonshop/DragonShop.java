package org.dragonitemc.dragonshop;

import com.ericlam.mc.eld.ELDBukkitPlugin;
import com.ericlam.mc.eld.ManagerProvider;
import com.ericlam.mc.eld.ServiceCollection;
import com.ericlam.mc.eld.annotations.ELDPlugin;
import com.ericlam.mc.eldgui.MVCInstallation;
import org.dragonitemc.dragonshop.api.ShopService;
import org.dragonitemc.dragonshop.config.GUITemplate;
import org.dragonitemc.dragonshop.config.Shop;
import org.dragonitemc.dragonshop.controller.ShopController;
import org.dragonitemc.dragonshop.services.ShopManager;

@ELDPlugin(
    lifeCycle = DragonShopLifeCycle.class,
    registry = DragonShopRegistry.class
)
public class DragonShop extends ELDBukkitPlugin {

    @Override
    protected void bindServices(ServiceCollection collection) {
        MVCInstallation mvc = collection.getInstallation(MVCInstallation.class);
        collection.addGroupConfiguration(GUITemplate.class);
        collection.addGroupConfiguration(Shop.class);

        collection.bindService(ShopService.class, ShopManager.class);

        mvc.registerControllers(ShopController.class);
    }

    @Override
    protected void manageProvider(ManagerProvider provider) {

    }

}
