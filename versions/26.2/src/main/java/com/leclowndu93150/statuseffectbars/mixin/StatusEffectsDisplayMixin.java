package com.leclowndu93150.statuseffectbars.mixin;

import com.leclowndu93150.statuseffectbars.config.StatusEffectBarsConfig;
import com.leclowndu93150.statuseffectbars.render.StatusEffectBarRenderer;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.EffectsInInventory;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(EffectsInInventory.class)
public abstract class StatusEffectsDisplayMixin {

    @Inject(
            method = "extractEffects",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/neoforged/neoforge/client/extensions/common/IClientMobEffectExtensions;extractInventoryText(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIII)Z"
            )
    )
    private void onDrawStatusEffectBackground(GuiGraphicsExtractor graphics,
                                              Collection<MobEffectInstance> activeEffects,
                                              int x0, int yStep, int mouseX, int mouseY, int maxWidth,
                                              CallbackInfo ci,
                                              @Local(name = "y0") int y0,
                                              @Local(name = "textureWidth") int textureWidth,
                                              @Local(name = "effect") MobEffectInstance effect) {
        StatusEffectBarRenderer.render(graphics, null, effect, x0, y0,
                textureWidth, EffectsInInventory.SPRITE_SQUARE_SIZE,
                StatusEffectBarsConfig.INSTANCE.inventoryLayout);
    }
}
