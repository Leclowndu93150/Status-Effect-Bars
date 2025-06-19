package com.leclowndu93150.statuseffectbars.mixin.compat;

import com.leclowndu93150.statuseffectbars.config.StatusEffectBarsConfig;
import com.leclowndu93150.statuseffectbars.render.StatusEffectBarRenderer;
import fuzs.stylisheffects.client.gui.effects.AbstractEffectRenderer;
import fuzs.stylisheffects.client.gui.effects.EffectWidget;
import fuzs.stylisheffects.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(value = AbstractEffectRenderer.class, remap = false)
public abstract class StylishEffectsCompatMixin implements EffectWidget {

    @Shadow
    protected Object screen;

    @Shadow
    public abstract double getWidgetScale();

    @Shadow
    protected abstract Optional<Component> getEffectDuration(MobEffectInstance mobEffectInstance);

    @Shadow protected abstract ClientConfig.EffectWidgetConfig widgetConfig();

    @Shadow protected abstract ClientConfig.EffectRendererConfig rendererConfig();

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
    private void statusEffectBars$renderSmallerDurationText(GuiGraphics guiGraphics, int posX, int posY, Minecraft minecraft, MobEffectInstance mobEffectInstance, CallbackInfo ci) {
        if (StatusEffectBarsConfig.INSTANCE.renderOldTimer.get()) {
            return;
        }

        ci.cancel();

        if (StatusEffectBarsConfig.INSTANCE.renderCustomTimer.get()) {
            if (this.widgetConfig().ambientDuration || !mobEffectInstance.isAmbient()) {
                getEffectDuration(mobEffectInstance).ifPresent((durationComponent) -> {
                    int textColor = 0xFFF8DC; // Beige color
                    int alpha = (int)(this.rendererConfig().widgetAlpha * 255.0F) << 24;
                    FormattedCharSequence text = durationComponent.getVisualOrderText();

                    // Scale and position at top left
                    float textScale = 0.6f;
                    int offsetX = 4; // offset from side
                    int offsetY = 4; // offset from top

                    guiGraphics.pose().pushPose();
                    guiGraphics.pose().translate(posX + offsetX, posY + offsetY, 0);
                    guiGraphics.pose().scale(textScale, textScale, 1.0f);

                    guiGraphics.drawString(minecraft.font, text, -1, 0, alpha, false);
                    guiGraphics.drawString(minecraft.font, text, 1, 0, alpha, false);
                    guiGraphics.drawString(minecraft.font, text, 0, -1, alpha, false);
                    guiGraphics.drawString(minecraft.font, text, 0, 1, alpha, false);
                    guiGraphics.drawString(minecraft.font, text, 0, 0, alpha | textColor, false);

                    guiGraphics.pose().popPose();
                });
            }
        }
    }
}