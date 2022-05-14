package org.dragonitemc.dragonshop;

import com.ericlam.mc.eld.ELDLifeCycle;
import org.bukkit.plugin.java.JavaPlugin;
import org.dragonitemc.dragonshop.api.ShopTaskService;
import org.dragonitemc.dragonshop.tasks.conditions.FlyingCondition;
import org.dragonitemc.dragonshop.tasks.conditions.PermissionCondition;
import org.dragonitemc.dragonshop.tasks.conditions.PlaceholderJSCondition;
import org.dragonitemc.dragonshop.tasks.prices.HasPermissionPrice;
import org.dragonitemc.dragonshop.tasks.prices.TakePermissionPrice;
import org.dragonitemc.dragonshop.tasks.rewards.*;

import javax.inject.Inject;

public class DragonShopLifeCycle implements ELDLifeCycle {
    @Inject
    private ShopTaskService taskService;

    @Override
    public void onEnable(JavaPlugin plugin) {

        taskService.addRewardTask(new CommandReward());
        taskService.addRewardTask(new PlayerCommandReward());
        taskService.addRewardTask(new MessageReward());
        taskService.addRewardTask(new CloseReward());
        taskService.addRewardTask(new PermissionReward());
        taskService.addRewardTask(new BungeeServerReward());

        taskService.addPriceTask(new HasPermissionPrice());
        taskService.addPriceTask(new TakePermissionPrice());

        taskService.addCondition(new PermissionCondition());
        taskService.addCondition(new PlaceholderJSCondition());
        taskService.addCondition(new FlyingCondition());

    }

    @Override
    public void onDisable(JavaPlugin plugin) {

    }

}
