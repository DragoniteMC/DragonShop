package org.dragonitemc.dragonshop.command;

import com.ericlam.mc.eld.annotations.CommandArg;
import com.ericlam.mc.eld.annotations.Commander;
import com.ericlam.mc.eld.components.CommandNode;
import com.ericlam.mc.eldgui.UINotFoundException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.api.ShopService;

import javax.inject.Inject;

@Commander(
        name = "open",
        permission = "dragonshop.open",
        description = "打開商店",
        playerOnly = true
)
public class DShopOpenCommand implements CommandNode {

    @CommandArg(order = 0)
    private String shopName;

    @Inject
    private ShopService shopService;


    @Override
    public void execute(CommandSender sender) {
        try {
            var player = (Player) sender;
            shopService.openShop(player, shopName);
        } catch (UINotFoundException e) { // 找不到該界面時
            sender.sendMessage("Shop " + shopName + " is not exist.");
        }
    }

}
