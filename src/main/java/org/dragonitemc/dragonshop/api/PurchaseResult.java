package org.dragonitemc.dragonshop.api;

import javax.annotation.Nullable;

public class PurchaseResult {

    private final boolean success;
    private String message;


    private PurchaseResult(boolean success, @Nullable String message) {
        this.success = success;
        this.message = message;
    }


    public static PurchaseResult failed(String reason){
        return new PurchaseResult(false, reason);
    }

    public static PurchaseResult success(){
        return new PurchaseResult(true, null);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }
}
