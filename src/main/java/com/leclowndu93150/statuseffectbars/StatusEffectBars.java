package com.leclowndu93150.statuseffectbars;

import com.leclowndu93150.statuseffectbars.config.StatusEffectBarsConfig;
import net.minecraft.client.gui.Gui;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(StatusEffectBars.MODID)
public class StatusEffectBars {
    public static final String MODID = "statuseffectbars";

    public StatusEffectBars(IEventBus modEventBus, ModContainer modContainer) {

        modContainer.registerConfig(ModConfig.Type.COMMON, StatusEffectBarsConfig.SPEC);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }


}
