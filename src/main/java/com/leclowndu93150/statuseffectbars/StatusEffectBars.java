package com.leclowndu93150.statuseffectbars;

import com.leclowndu93150.statuseffectbars.config.StatusEffectBarsConfig;
import com.leclowndu93150.statuseffectbars.network.NetworkHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(StatusEffectBars.MODID)
public class StatusEffectBars {
    public static final String MODID = "statuseffectbars";

    public StatusEffectBars() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, StatusEffectBarsConfig.SPEC);
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::init);
    }

}

