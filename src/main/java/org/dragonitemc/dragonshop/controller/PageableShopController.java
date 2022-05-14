package org.dragonitemc.dragonshop.controller;

import com.ericlam.mc.eld.configurations.Page;
import com.ericlam.mc.eld.configurations.PageRequest;
import com.ericlam.mc.eldgui.UISession;
import com.ericlam.mc.eldgui.controller.UIController;
import com.ericlam.mc.eldgui.event.ClickMapping;
import com.ericlam.mc.eldgui.view.BukkitView;
import org.bukkit.entity.Player;
import org.dragonitemc.dragonshop.ShopException;
import org.dragonitemc.dragonshop.config.Shop;
import org.dragonitemc.dragonshop.model.PageablePlayerShop;
import org.dragonitemc.dragonshop.view.PageableShopView;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@UIController("dshop.pageable")
public class PageableShopController extends AbstractShopController {

    private static final long PAGE_SIZE = 45L;

    @Override
    public BukkitView<?, ?> indexView(Player player, UISession session, Shop shop) {
        int page = Optional.ofNullable((Integer) session.getAttribute("page")).orElse(1);
        var content = shop.shopItems.entrySet().stream().skip((page - 1) * PAGE_SIZE).limit(PAGE_SIZE).toList();
        var pageable = new PlayerShopPage(content, page, shop.shopItems.size());
        session.setAttribute("current", pageable);
        var playerShop = new PageablePlayerShop(shop.title, pageable, player);
        return new BukkitView<>(PageableShopView.class, playerShop);
    }

    @ClickMapping(view = PageableShopView.class, pattern = 'B')
    public BukkitView<?, ?> onPreviousPage(Player player, UISession session) {
        Page<Map.Entry<String, Shop.ShopItemInfo>> page = session.getAttribute("current");
        if (page == null) {
            throw new ShopException("分頁無效", "無法定位目前的頁面");
        }
        if (!page.hasPrevious()) {
            player.sendMessage("沒有上一頁。");
            return null;
        }
        session.setAttribute("page", page.getCurrentPage() - 1);
        return index(player, session);
    }

    @ClickMapping(view = PageableShopView.class, pattern = 'C')
    public BukkitView<?, ?> onNextPage(Player player, UISession session) {
        Page<Map.Entry<String, Shop.ShopItemInfo>> page = session.getAttribute("current");
        if (page == null) {
            throw new ShopException("分頁無效", "無法定位目前的頁面");
        }
        if (!page.hasNext()) {
            player.sendMessage("沒有下一頁。");
            return null;
        }
        session.setAttribute("page", page.getCurrentPage() + 1);
        return index(player, session);
    }


    public static class PlayerShopPage implements Page<Map.Entry<String, Shop.ShopItemInfo>> {

        private final List<Map.Entry<String, Shop.ShopItemInfo>> content;
        private final int page;
        private final long totalSize;
        private final int totalPages;
        private final boolean hasNext;

        public PlayerShopPage(List<Map.Entry<String, Shop.ShopItemInfo>> content, int page, long totalSize) {
            this.content = content;
            this.page = page;
            this.totalSize = totalSize;
            this.totalPages = (int) Math.ceil((double) totalSize / PAGE_SIZE);
            this.hasNext = page < totalPages;
        }


        @Override
        public List<Map.Entry<String, Shop.ShopItemInfo>> getContent() {
            return content;
        }

        @Override
        public boolean hasContent() {
            return !content.isEmpty();
        }

        @Override
        public int getCurrentPage() {
            return page;
        }

        @Override
        public int getTotalPages() {
            return totalPages;
        }

        @Override
        public boolean hasNext() {
            return hasNext;
        }

        @Override
        public boolean hasPrevious() {
            return getCurrentPage() > 1;
        }

        @Override
        public long getTotalElements() {
            return totalSize;
        }

        @Override
        public PageRequest getPageRequest() {
            throw new UnsupportedOperationException("cannot get page request");
        }

        @Override
        public <U> Page<U> map(Function<Map.Entry<String, Shop.ShopItemInfo>, U> function) {
            throw new UnsupportedOperationException("cannot map to other");
        }
    }
}
