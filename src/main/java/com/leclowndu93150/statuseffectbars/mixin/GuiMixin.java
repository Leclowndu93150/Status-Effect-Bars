package com.leclowndu93150.statuseffectbars.mixin;

import com.leclowndu93150.statuseffectbars.config.StatusEffectBarsConfig;
import com.leclowndu93150.statuseffectbars.render.StatusEffectBarRenderer;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Renders the duration bars under status effects on the HUD.
 */
@Mixin(Gui.class)
public abstract class GuiMixin {

    @Inject(
            method = "renderEffects",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V",
                    shift = At.Shift.AFTER
            )
    )
    private void onDrawStatusEffectBackground(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci,
                                              @Local(ordinal = 2) int i, // x position
                                              @Local(ordinal = 3) int j, // y position
                                              @Local MobEffectInstance mobeffectinstance) {
        StatusEffectBarRenderer.render(guiGraphics, deltaTracker, mobeffectinstance, i, j, 24, 24, StatusEffectBarsConfig.INSTANCE.hudLayout);
    }
}