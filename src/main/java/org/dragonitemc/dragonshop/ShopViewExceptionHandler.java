package org.dragonitemc.dragonshop;

import com.ericlam.mc.eldgui.UISession;
import com.ericlam.mc.eldgui.exception.ExceptionViewHandler;
import com.ericlam.mc.eldgui.exception.HandleException;
import com.ericlam.mc.eldgui.exception.HandleForControllers;
import com.ericlam.mc.eldgui.view.BukkitView;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.controller.ShopController;
import org.dragonitemc.dragonshop.view.ShopErrorView;

@HandleForControllers(ShopController.class)
public class ShopViewExceptionHandler implements ExceptionViewHandler {

    @Override
    public BukkitView<?, ?> createErrorView(Exception e, String s, UISession uiSession, Player player) {
        e.printStackTrace(); // 這個要報錯
        var shopException = new ShopException("商店執行時出現錯誤", e.getMessage());
        return new BukkitView<>(ShopErrorView.class, shopException);
    }

    // 這個別報錯
    @HandleException(ShopException.class)
    public BukkitView<?, ?> createShopError(ShopException e, String s, UISession uiSession, Player player) {
        return new BukkitView<>(ShopErrorView.class, e);
    }
}
