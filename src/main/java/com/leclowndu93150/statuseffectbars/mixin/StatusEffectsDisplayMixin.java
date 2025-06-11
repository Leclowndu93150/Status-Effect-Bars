package com.leclowndu93150.statuseffectbars.mixin;

import com.leclowndu93150.statuseffectbars.config.StatusEffectBarsConfig;
import com.leclowndu93150.statuseffectbars.render.StatusEffectBarRenderer;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Renders the duration bars under status effects.
 */
@Mixin(EffectRenderingInventoryScreen.class)
public abstract class StatusEffectsDisplayMixin {

    @Inject(method = "renderBackgrounds",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V", shift = At.Shift.AFTER))
    private void onDrawStatusEffectBackground(GuiGraphics context, int x, int height, Iterable<MobEffectInstance> effects, boolean wide, CallbackInfo ci, @Local(ordinal = 2) int y, @Local MobEffectInstance effect) {
        StatusEffectBarRenderer.render(context, null, effect, x, y, wide ? 120 : 32, 32, StatusEffectBarsConfig.INSTANCE.inventoryLayout);
    }

}