package com.leclowndu93150.statuseffectbars.mixin.compat;

import com.leclowndu93150.statuseffectbars.config.StatusEffectBarsConfig;
import com.leclowndu93150.statuseffectbars.render.StatusEffectBarRenderer;
import fuzs.stylisheffects.client.gui.effects.AbstractEffectRenderer;
import fuzs.stylisheffects.client.gui.effects.EffectWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AbstractEffectRenderer.class, remap = false)
public abstract class StylishEffectsCompatMixin implements EffectWidget {

    @Shadow
    protected Object screen;

    @Shadow
    public abstract double getWidgetScale();

    @Inject(method = "renderWidget", at = @At("TAIL"), remap = false)
    private void statusEffectBars$addProgressBar(GuiGraphics guiGraphics, int posX, int posY, Minecraft minecraft, MobEffectInstance mobEffectInstance, CallbackInfo ci) {
        boolean isGui = this.screen instanceof Gui;
        StatusEffectBarsConfig.LayoutConfig config = isGui ? StatusEffectBarsConfig.INSTANCE.hudLayout : StatusEffectBarsConfig.INSTANCE.inventoryLayout;

        if (!config.enabled.get()) return;

        // Calculate actual position and size with scaling
        double scale = this.getWidgetScale();
        int actualX = (int)(posX * scale);
        int actualY = (int)(posY * scale);
        int actualWidth = (int)(getWidth() * scale);
        int actualHeight = (int)(getHeight() * scale);

        StatusEffectBarRenderer.render(guiGraphics, mobEffectInstance, actualX, actualY, actualWidth, actualHeight, config);
    }

    @Inject(method = "drawEffectText", at = @At("HEAD"), cancellable = true, remap = false)
    private void statusEffectBars$cancelDurationText(GuiGraphics guiGraphics, int posX, int posY, Minecraft minecraft, MobEffectInstance mobEffectInstance, CallbackInfo ci) {
        boolean isGui = this.screen instanceof Gui;
        StatusEffectBarsConfig.LayoutConfig config = isGui ? StatusEffectBarsConfig.INSTANCE.hudLayout : StatusEffectBarsConfig.INSTANCE.inventoryLayout;

        if (config.enabled.get()) {
            ci.cancel();
        }
    }
}