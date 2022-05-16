package org.dragonitemc.dragonshop.tasks.rewards;

import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.api.RewardTask;

public class PlaySoundReward extends RewardTask<PlaySoundReward.PlaySound> {


    public PlaySoundReward() {
        super("sound");
    }

    @Override
    public void giveReward(PlaySound content, Player player) {
        player.playSound(player.getLocation(), content.sound, content.volume, content.pitch);
    }

    public static class PlaySound {

        public String sound;
        public float volume;
        public float pitch;
    }
}
