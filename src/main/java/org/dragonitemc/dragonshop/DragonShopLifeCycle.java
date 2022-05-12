package org.dragonitemc.dragonshop;

import com.ericlam.mc.eld.ELDLifeCycle;
import com.ericlam.mc.eld.services.ScheduleService;
import org.bukkit.plugin.java.JavaPlugin;
import org.dragonitemc.dragonshop.api.ShopService;
import org.dragonitemc.dragonshop.api.ShopTaskService;
import org.dragonitemc.dragonshop.tasks.CommandReward;
import org.dragonitemc.dragonshop.tasks.MessageReward;
import org.dragonitemc.dragonshop.tasks.PlayerCommandReward;
import org.dragonitemc.dragonshop.tasks.TestAsyncReward;
import org.dragonitemc.dragonshop.view.ShopOneLineView;
import org.dragonitemc.dragonshop.view.ShopTwoLineView;

import javax.inject.Inject;

public class DragonShopLifeCycle implements ELDLifeCycle {

    @Inject
    private ShopService shopService;

    @Inject
    private ShopTaskService taskService;


    @Inject
    private ScheduleService scheduleService;

    @Override
    public void onEnable(JavaPlugin plugin) {
        shopService.addView("one-line", ShopOneLineView.class);
        shopService.addView("two-line", ShopTwoLineView.class);


        taskService.addRewardTask(new CommandReward());
        taskService.addRewardTask(new PlayerCommandReward());
        taskService.addRewardTask(new MessageReward());
        taskService.addRewardTask(new TestAsyncReward(scheduleService, plugin));

    }

    @Override
    public void onDisable(JavaPlugin plugin) {

    }

}
