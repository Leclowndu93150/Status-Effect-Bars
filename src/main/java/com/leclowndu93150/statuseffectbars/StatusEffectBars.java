package com.leclowndu93150.statuseffectbars;

import com.leclowndu93150.statuseffectbars.config.StatusEffectBarsConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(StatusEffectBars.MODID)
public class StatusEffectBars {
    public static final String MODID = "statuseffectbars";

    public StatusEffectBars() {

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, StatusEffectBarsConfig.SPEC);
    }
}
