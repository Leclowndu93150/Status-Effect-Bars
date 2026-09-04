package com.leclowndu93150.statuseffectbars.mixin;

import com.leclowndu93150.statuseffectbars.config.StatusEffectBarsConfig;
import com.leclowndu93150.statuseffectbars.render.StatusEffectBarRenderer;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hud.class)
public abstract class HudMixin {

    @Unique private static final int statusEffectBars$ICON_SIZE = 24;

    @Inject(
            method = "extractEffects",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Hud;getMobEffectSprite(Lnet/minecraft/core/Holder;)Lnet/minecraft/resources/Identifier;",
                    ordinal = 0
            )
    )
    private void onExtractEffects(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci,
                                  @Local(name = "instance") MobEffectInstance instance,
                                  @Local(name = "x") int x,
                                  @Local(name = "y") int y) {
        StatusEffectBarRenderer.render(graphics, deltaTracker, instance, x, y,
                statusEffectBars$ICON_SIZE, statusEffectBars$ICON_SIZE,
                StatusEffectBarsConfig.INSTANCE.hudLayout);
    }
}
