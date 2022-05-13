package org.dragonitemc.dragonshop.component;

import com.ericlam.mc.eld.services.ItemStackService;
import com.ericlam.mc.eldgui.component.ComponentFactory;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Consumer;

public interface PlaceholderItemFactory extends ComponentFactory<PlaceholderItemFactory> {

    PlaceholderItemFactory placeholderDisplay(String name);

    PlaceholderItemFactory placeholderLore(List<String> lore);

    PlaceholderItemFactory updateInterval(int seconds);

    PlaceholderItemFactory setPlaceholderPlayer(Player player);

    PlaceholderItemFactory editItem(Consumer<ItemStackService.ItemFactory> factoryConsumer);


}
