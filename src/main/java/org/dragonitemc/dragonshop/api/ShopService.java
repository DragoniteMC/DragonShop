package org.dragonitemc.dragonshop.api;

import com.ericlam.mc.eldgui.UINotFoundException;
import com.ericlam.mc.eldgui.UISession;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.config.Shop;

import java.util.function.Consumer;

public interface ShopService {

    void openShop(Player player, Shop shop) throws UINotFoundException;

    void openShop(Player player, Shop shop, Consumer<UISession> sessionConsumer) throws UINotFoundException;

}
