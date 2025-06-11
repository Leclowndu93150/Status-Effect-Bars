package com.leclowndu93150.statuseffectbars.render;

import com.leclowndu93150.statuseffectbars.config.StatusEffectBarsConfig;
import com.leclowndu93150.statuseffectbars.duck.StatusEffectInstanceDuck;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;

import javax.annotation.Nullable;

public class StatusEffectBarRenderer {

    @SuppressWarnings("SuspiciousNameCombination")
    public static void render(GuiGraphics context, MobEffectInstance effect, int x, int y, int width, int height, StatusEffectBarsConfig.LayoutConfig layoutConfig) {
        // Special cases where the bar is hidden

        if (!layoutConfig.enabled.get()) return;

        if (effect.getDuration() > StatusEffectBarsConfig.INSTANCE.maxRemainingDuration.get() || effect.isInfiniteDuration()) {
            return; // Too much time remaining
        }

        StatusEffectInstanceDuck duck = (StatusEffectInstanceDuck) effect;
        int age = duck.statusEffectBars_getMaxDuration() - effect.getDuration();
        if (effect.isAmbient() && age < StatusEffectBarsConfig.INSTANCE.minAmbientAge.get()) {
            return; // Beacon effect too recent (will probably be refreshed soon)
        }

        if (layoutConfig.direction.get().swapXY) {
            int tmp = width;
            width = height;
            height = tmp;
        }

        // start--------+-----end
        // |            |       |
        // +---------middle-----+
        // or
        // start----+
        // |        |
        // |        |
        // +---middle
        // |        |
        // end------+
        int startX, middleX, endX;
        int startY, middleY, endY;

        startX = layoutConfig.collinearOffset.get() + layoutConfig.collinearPadding.get();
        endX = layoutConfig.collinearOffset.get() + width - layoutConfig.collinearPadding.get();
        if (layoutConfig.direction.get().reverseAxis) {
            int tmp = startX;
            startX = endX;
            endX = tmp;
        }

        float tickDelta = Minecraft.getInstance().getPartialTick();

        float progress = (effect.getDuration() - tickDelta) / ((StatusEffectInstanceDuck) effect).statusEffectBars_getMaxDuration();
        //TODO: Casting to int ??
        middleX = (int) Mth.lerp(progress, startX, endX);

        startY = layoutConfig.orthogonalOffset.get();
        if (layoutConfig.relativeToEnd.get()) {
            startY = height - (startY + layoutConfig.thickness.get());
        }
        middleY = startY + layoutConfig.thickness.get();
        endY = startY;

        if (layoutConfig.direction.get().swapXY) {
            // Swapping X and Y to make the bar vertical instead of horizontal
            int tmp;

            tmp = startX;
            startX = startY;
            startY = tmp;

            tmp = middleX;
            middleX = middleY;
            middleY = tmp;

            tmp = endX;
            endX = endY;
            endY = tmp;
        }

        startX += x;
        middleX += x;
        endX += x;
        startY += y;
        middleY += y;
        endY += y;

        context.fill(startX, startY, middleX, middleY, StatusEffectBarsConfig.INSTANCE.getColor(effect));
        context.fill(middleX, middleY, endX, endY, StatusEffectBarsConfig.INSTANCE.backgroundColor.get());
    }

}