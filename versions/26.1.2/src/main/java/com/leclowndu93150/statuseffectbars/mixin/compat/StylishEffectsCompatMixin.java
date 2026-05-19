package com.leclowndu93150.statuseffectbars.mixin.compat;

import com.leclowndu93150.statuseffectbars.config.StatusEffectBarsConfig;
import com.leclowndu93150.statuseffectbars.render.StatusEffectBarRenderer;
import com.mojang.datafixers.util.Either;
import fuzs.stylisheffects.common.client.gui.screens.inventory.effects.AbstractMobEffectRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AbstractMobEffectRenderer.class, remap = false)
public abstract class StylishEffectsCompatMixin {

    @Shadow
    protected Either<Gui, AbstractContainerScreen<?>> environment;

    @Shadow
    public abstract int getWidth();

    @Shadow
    public abstract int getHeight();

    @Inject(method = "renderContents", at = @At("TAIL"), remap = false)
    private void statusEffectBars$addProgressBar(GuiGraphicsExtractor guiGraphics, int posX, int posY, MobEffectInstance mobEffect, CallbackInfo ci) {
        boolean isGui = this.environment.left().isPresent();
        StatusEffectBarsConfig.LayoutConfig config = isGui
                ? StatusEffectBarsConfig.INSTANCE.hudLayout
                : StatusEffectBarsConfig.INSTANCE.inventoryLayout;

        if (!config.enabled.get()) return;

        StatusEffectBarRenderer.render(guiGraphics, null, mobEffect, posX, posY, this.getWidth(), this.getHeight(), config);
    }
}
