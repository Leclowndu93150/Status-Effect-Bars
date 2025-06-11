package com.leclowndu93150.statuseffectbars.config;


import net.minecraft.client.resources.language.I18n;

@SuppressWarnings("unused")
public enum Direction {
    LEFT_TO_RIGHT(false, false),
    RIGHT_TO_LEFT(false, true),
    BOTTOM_TO_TOP(true, true),
    TOP_TO_BOTTOM(true, false);

    public final boolean swapXY;
    public final boolean reverseAxis;

    Direction(boolean swapXY, boolean reverseAxis) {
        this.swapXY = swapXY;
        this.reverseAxis = reverseAxis;
    }

    @Override
    public String toString() {
        return I18n.get("text.autoconfig.status-effect-bars.option.direction." + name());
    }

}