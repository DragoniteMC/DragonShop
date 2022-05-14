package org.dragonitemc.dragonshop.command;

import com.ericlam.mc.eld.annotations.Commander;
import com.ericlam.mc.eld.annotations.InjectPool;
import com.ericlam.mc.eld.components.CommandNode;
import com.ericlam.mc.eld.configurations.GroupConfig;
import org.bukkit.command.CommandSender;
import org.dragonitemc.dragonshop.config.Shop;

@Commander(
        name = "reload",
        description = "Reload the plugin",
        permission = "dragonshop.reload"
)
public class DShopReloadCommand implements CommandNode {

    @InjectPool
    private GroupConfig<Shop> shopGroupConfig;

    @Override
    public void execute(CommandSender commandSender) {
        shopGroupConfig.fetch();
        commandSender.sendMessage("§aReload success!");
    }
}
