package org.dragonitemc.dragonshop;

import com.ericlam.mc.eld.registrations.CommandRegistry;
import com.ericlam.mc.eld.registrations.ComponentsRegistry;
import com.ericlam.mc.eld.registrations.ListenerRegistry;
import org.dragonitemc.dragonshop.command.DShopCommand;

public class DragonShopRegistry implements ComponentsRegistry {


    @Override
    public void registerCommand(CommandRegistry registry) {
        registry.command(DShopCommand.class);
    }

    @Override
    public void registerListeners(ListenerRegistry registry) {

    }
}
