package org.dragonitemc.dragonshop.config;

import com.ericlam.mc.eld.annotations.Prefix;
import com.ericlam.mc.eld.annotations.Resource;
import com.ericlam.mc.eld.components.LangConfiguration;

@Prefix(path = "prefix")
@Resource(locate = "lang.yml")
public class DragonShopMessage extends LangConfiguration {
}
