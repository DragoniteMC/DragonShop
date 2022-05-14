package org.dragonitemc.dragonshop.tasks.conditions;

import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.api.Condition;

public class FlyingCondition extends Condition<Boolean> {

    public FlyingCondition() {
        super("flying");
    }

    @Override
    public boolean isMatched(Boolean value, Player player) {
        return player.isFlying() == value;
    }
}
