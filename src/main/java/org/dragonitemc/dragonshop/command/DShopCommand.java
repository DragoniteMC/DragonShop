package org.dragonitemc.dragonshop.command;

import com.ericlam.mc.eld.annotations.CommandArg;
import com.ericlam.mc.eld.annotations.Commander;
import com.ericlam.mc.eld.annotations.InjectPool;
import com.ericlam.mc.eld.components.CommandNode;
import com.ericlam.mc.eld.configurations.GroupConfig;
import com.ericlam.mc.eldgui.InventoryService;
import com.ericlam.mc.eldgui.UIDispatcher;
import com.ericlam.mc.eldgui.UINotFoundException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.api.ShopService;
import org.dragonitemc.dragonshop.config.Shop;

import javax.inject.Inject;
import java.util.Optional;

@Commander(
        name = "dshop",
        description = "DragonShop command",
        playerOnly = true
)
public class DShopCommand implements CommandNode {

    @CommandArg(order = 0)
    private String shopName;

    @InjectPool
    private GroupConfig<Shop> shopGroupConfig;

    @Inject
    private ShopService shopService;

    @Override
    public void execute(CommandSender sender) {
        Optional<Shop> shop = shopGroupConfig.findById(shopName);
        if (shop.isEmpty()){
            sender.sendMessage("Shop " + shopName + " is not exist.");
            return;
        }
        var player = (Player) sender;
        var shopContent = shop.get();
        try {
            shopService.openShop(player, shopContent);
        } catch (UINotFoundException e) { // 找不到該界面時
            player.sendMessage("UI not found.");
        }
    }

}
