package org.dragonitemc.dragonshop.api;

import org.bukkit.entity.Player;

public abstract class Condition<T> {

    private final String name;


    public Condition(String name) {
        this.name = name;
    }


    public abstract boolean isMatched(T value, Player player);


    public String getName() {
        return name;
    }
}
