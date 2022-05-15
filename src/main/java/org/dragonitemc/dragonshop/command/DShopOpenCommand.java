package org.dragonitemc.dragonshop.command;

import com.ericlam.mc.eld.annotations.CommandArg;
import com.ericlam.mc.eld.annotations.Commander;
import com.ericlam.mc.eld.components.CommandNode;
import com.ericlam.mc.eldgui.UINotFoundException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.api.ShopService;
import org.dragonitemc.dragonshop.config.DragonShopMessage;

import javax.inject.Inject;

@Commander(
        name = "open",
        description = "打開商店",
        playerOnly = true
)
public class DShopOpenCommand implements CommandNode {

    @CommandArg(order = 0)
    private String shopName;

    @Inject
    private ShopService shopService;

    @Inject
    private DragonShopMessage message;


    @Override
    public void execute(CommandSender sender) {
        try {
            if (!sender.hasPermission("dragonshop.open."+shopName)){
                sender.sendMessage(message.getLang().get("no-permission"));
                return;
            }
            var player = (Player) sender;
            shopService.openShop(player, shopName);
        } catch (UINotFoundException e) { // 找不到該界面時
            sender.sendMessage("Shop " + shopName + " is not exist.");
        }
    }

}
