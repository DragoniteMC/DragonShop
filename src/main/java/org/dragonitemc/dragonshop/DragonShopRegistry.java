package org.dragonitemc.dragonshop;

import com.ericlam.mc.eld.registrations.CommandRegistry;
import com.ericlam.mc.eld.registrations.ComponentsRegistry;
import com.ericlam.mc.eld.registrations.ListenerRegistry;
import org.dragonitemc.dragonshop.command.DShopCommand;
import org.dragonitemc.dragonshop.command.DShopOpenCommand;
import org.dragonitemc.dragonshop.command.DShopReloadCommand;

public class DragonShopRegistry implements ComponentsRegistry {


    @Override
    public void registerCommand(CommandRegistry registry) {
        registry.command(DShopCommand.class, cc ->{
            cc.command(DShopOpenCommand.class);
            cc.command(DShopReloadCommand.class);
        });
    }

    @Override
    public void registerListeners(ListenerRegistry registry) {

    }
}
