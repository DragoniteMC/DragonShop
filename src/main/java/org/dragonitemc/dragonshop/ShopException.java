package org.dragonitemc.dragonshop;

public class ShopException extends RuntimeException {
    private final String title;

    public ShopException(String title, String message) {
        super(message);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
