package org.dragonitemc.dragonshop.tasks.conditions;

import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.api.Condition;

import java.util.List;

public class PermissionCondition extends Condition<List<String>> {

    public PermissionCondition() {
        super("permission");
    }

    @Override
    public boolean isMatched(List<String> value, Player player) {
        return value.stream().allMatch(player::hasPermission);
    }
}
