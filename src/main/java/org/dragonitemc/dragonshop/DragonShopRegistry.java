package org.dragonitemc.dragonshop;

import com.ericlam.mc.eld.bukkit.CommandNode;
import com.ericlam.mc.eld.bukkit.ComponentsRegistry;
import com.ericlam.mc.eld.registration.CommandRegistry;
import com.ericlam.mc.eld.registration.ListenerRegistry;
import org.bukkit.event.Listener;
import org.dragonitemc.dragonshop.command.DShopCommand;
import org.dragonitemc.dragonshop.command.DShopOpenCommand;
import org.dragonitemc.dragonshop.command.DShopReloadCommand;

public class DragonShopRegistry implements ComponentsRegistry {


    @Override
    public void registerCommand(CommandRegistry<CommandNode> commandRegistry) {
        commandRegistry.command(DShopCommand.class, cc -> {
            cc.command(DShopOpenCommand.class);
            cc.command(DShopReloadCommand.class);
        });
    }

    @Override
    public void registerListeners(ListenerRegistry<Listener> listenerRegistry) {

    }
}
