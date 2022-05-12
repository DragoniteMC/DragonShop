package org.dragonitemc.dragonshop.tasks;

import com.ericlam.mc.eld.services.ScheduleService;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.dragonitemc.dragonshop.api.AsyncRewardTask;

public class TestAsyncReward extends AsyncRewardTask<Integer> {

    private final ScheduleService scheduleService;
    private final Plugin plugin;

    public TestAsyncReward(ScheduleService scheduleService, Plugin plugin) {
        super("async-test");

        this.plugin = plugin;
        this.scheduleService = scheduleService;
    }

    @Override
    public ScheduleService.BukkitPromise<Void> giveRewardAsync(Integer content, Player player) {
        return scheduleService.runAsync(plugin, () -> {
            try {
                player.sendMessage("sleeping...");
                Thread.sleep(1000L * content);
                player.sendMessage("wake up!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
