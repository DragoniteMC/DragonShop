package org.dragonitemc.dragonshop.tasks.conditions;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.api.Condition;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class PlaceholderJSCondition extends Condition<String> {

    private final ScriptEngine javaScript;

    public PlaceholderJSCondition() {
        super("placeholder-js");
        ScriptEngineManager manager = new ScriptEngineManager();
        javaScript = manager.getEngineByName("nashorn");
    }

    @Override
    public boolean isMatched(String value, Player player) {
        try {
            return (Boolean)javaScript.eval(PlaceholderAPI.setPlaceholders(player, value));
        } catch (ScriptException e) {
            e.printStackTrace();
            return false;
        }
    }
}
