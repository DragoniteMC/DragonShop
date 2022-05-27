package org.dragonitemc.dragonshop.config;

import com.ericlam.mc.eld.annotations.GroupResource;
import com.ericlam.mc.eld.components.GroupConfiguration;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@GroupResource(
        folder = "shops",
        preloads = {"example1", "example2", "example3"}
)
public class Shop extends GroupConfiguration {

    // 商店名稱
    public String title = "Unknown Title";
    public String guiType = "normal";
    public String viewType;

    // 商店物品列表
    public Map<String, ShopItemInfo> shopItems = new HashMap<>();

    public static class ShopItemInfo {

        public String name;
        public List<String> lore;
        public Material material;

        public int slot;

        public List<Integer> slots;

        public int amount;

        public int data;

        public int damage;
        public String toShop;

        public Map<ClickType, ClickHandle> handles = new HashMap<>();

        public List<ConditionInfo> conditions = new ArrayList<>();

        public ShopItemInfo() {
            this.name = "Unknown";
            this.lore = new ArrayList<>();
            this.material = Material.STONE;
            this.slot = -1;
            this.amount = 1;
        }

    }

    public static class ConditionInfo {

        public String type;
        public Object condition;

        public boolean revert = false;

    }

    public static class ClickHandle {

        public List<RewardInfo> rewards;
        public List<PriceInfo> prices;
    }

    public static class RewardInfo {

        public String type;
        public Object reward;

    }

    public static class PriceInfo {

        public String type;
        public Object price;

        public String failedMessage;

    }


}
