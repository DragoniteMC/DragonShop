package org.dragonitemc.dragonshop.api;

import javax.annotation.Nullable;
import java.lang.reflect.Type;

public interface ShopTask {

    @Nullable
    default Type getType(){
        return null;
    }

    String getName();

}
