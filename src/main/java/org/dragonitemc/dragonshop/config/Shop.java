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
        preloads = {"example"}
)
public class Shop extends GroupConfiguration {

    // 商店名稱
    public String title = "Unknown Title";
    public String guiType = "normal";

    // 商店物品列表
    public Map<String, ShopItemInfo> shopItems = new HashMap<>();
    
    public static class ShopItemInfo {

        public String name;
        public List<String> lore;
        public Material material;

        public int slot;

        public int amount;

        public String toShop;

        public Map<ClickType, RewardInfo> rewards = new HashMap<>();

        public Map<ClickType, PriceInfo> prices = new HashMap<>();

        public ShopItemInfo(){
            this.name = "Unknown";
            this.lore = new ArrayList<>();
            this.material = Material.STONE;
            this.slot = -1;
            this.amount = 1;
        }

    }

    public static class RewardInfo {

        public String rewardType;
        public Object reward;

    }

    public static class PriceInfo {

        public String priceType;
        public Object price;

    }
    


}
