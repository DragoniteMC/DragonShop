package org.dragonitemc.dragonshop.component;

import com.ericlam.mc.eld.services.ItemStackService;
import com.ericlam.mc.eldgui.component.AttributeController;
import com.ericlam.mc.eldgui.component.Component;
import com.ericlam.mc.eldgui.component.factory.AbstractComponentFactory;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PlaceholderItemFactoryImpl extends AbstractComponentFactory<PlaceholderItemFactory> implements PlaceholderItemFactory {

    private Player player;
    private int updateInterval;
    private String papiDisplay;
    private List<String> papiLore;

    public PlaceholderItemFactoryImpl(ItemStackService itemStackService, AttributeController attributeController) {
        super(itemStackService, attributeController);
    }

    @Override
    protected void defaultProperties() {
        this.player = null;
        this.updateInterval = 5;
        this.papiDisplay = "";
        this.papiLore = new ArrayList<>();
    }

    @Override
    public Component build(ItemStackService.ItemFactory itemFactory) {
        if (player == null) throw new IllegalStateException("you must specify a player to use placeholder");
        return new PlaceholderItem(attributeController, itemFactory, player, updateInterval, papiDisplay, papiLore);
    }

    @Override
    public PlaceholderItemFactory placeholderDisplay(String name) {
        this.papiDisplay = name;
        return this;
    }

    @Override
    public PlaceholderItemFactory placeholderLore(List<String> lore) {
        this.papiLore = lore;
        return this;
    }

    @Override
    public PlaceholderItemFactory updateInterval(int seconds) {
        this.updateInterval = seconds;
        return this;
    }

    @Override
    public PlaceholderItemFactory setPlaceholderPlayer(Player player) {
        this.player = player;
        return this;
    }

    @Override
    public PlaceholderItemFactory editItem(Consumer<ItemStackService.ItemFactory> factoryConsumer) {
        return editItemByFactory(factoryConsumer);
    }
}
