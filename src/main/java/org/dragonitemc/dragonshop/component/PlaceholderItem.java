package org.dragonitemc.dragonshop.component;

import com.ericlam.mc.eld.services.ItemStackService;
import com.ericlam.mc.eldgui.component.AbstractComponent;
import com.ericlam.mc.eldgui.component.AttributeController;
import com.ericlam.mc.eldgui.component.modifier.Animatable;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.dragonitemc.dragonshop.DragonShop;

import java.util.List;

public class PlaceholderItem extends AbstractComponent implements Animatable {

    private final Player player;
    private final int updateInterval;
    private final String papiDisplay;
    private final List<String> papiLore;


    private BukkitTask task = null;

    public PlaceholderItem(AttributeController attributeController,
                           ItemStackService.ItemFactory itemFactory,
                           Player player,
                           int updateInterval,
                           String papiDisplay,
                           List<String> papiLore
    ) {
        super(attributeController, itemFactory);
        this.player = player;
        this.updateInterval = updateInterval;
        this.papiDisplay = papiDisplay;
        this.papiLore = papiLore;

        itemFactory.display(papiDisplay);
        itemFactory.lore(papiLore);

        updateInventory();
    }

    @Override
    public void startAnimation() {
        // only run if there is a placeholder in display or lore
        if (this.updateInterval > 0 && this.task == null && (hasPapiInDisplay() || hasPapiInLore())) {
            task = new PlaceholderRunnable().runTaskTimer(DragonShop.getProvidingPlugin(DragonShop.class), 0, this.updateInterval * 20L);
        }
    }

    @Override
    public boolean isAnimating() {
        return task != null && !task.isCancelled();
    }

    @Override
    public void stopAnimation() {
        if (this.task == null || this.task.isCancelled()) return;
        this.task.cancel();
        this.task = null;
    }

    private boolean hasPapiInDisplay(){
        return this.papiDisplay != null && this.papiDisplay.matches("\\%(.+)\\%");
    }

    private boolean hasPapiInLore(){
        return this.papiLore != null && this.papiLore.stream().anyMatch(s -> s.matches("\\%(.+)\\%"));
    }


    private class PlaceholderRunnable extends BukkitRunnable {

        @Override
        public void run() {
            var toDisplay = PlaceholderAPI.setPlaceholders(player, papiDisplay);
            var toLore = PlaceholderAPI.setPlaceholders(player, papiLore);

            itemFactory.display(toDisplay);
            itemFactory.lore(toLore);

            updateInventory();
        }
    }
}
