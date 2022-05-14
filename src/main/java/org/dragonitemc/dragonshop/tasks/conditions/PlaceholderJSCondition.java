package org.dragonitemc.dragonshop.tasks.conditions;

import com.ericlam.mc.eld.ELDependenci;
import com.ericlam.mc.eld.services.LoggingService;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.api.Condition;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class PlaceholderJSCondition extends Condition<String> {

    private final ScriptEngine javaScript;

    public PlaceholderJSCondition() {
        super("placeholder-js");
        NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        this.javaScript = factory.getScriptEngine("-scripting", "--no-java");
    }

    @Override
    public boolean isMatched(String value, Player player) {
        if (this.javaScript == null) {
            LoggerFactory.getLogger(PlaceholderJSCondition.class).error("No Script Engines found, returning false");
            return false;
        }
        try {
            var js = PlaceholderAPI.setPlaceholders(player, value);
            ELDependenci.getApi().exposeService(LoggingService.class).getLogger(PlaceholderJSCondition.class).debug("Evaluating JS: " + js);
            return (Boolean) javaScript.eval(js);
        } catch (ScriptException e) {
            e.printStackTrace();
            return false;
        }
    }
}
