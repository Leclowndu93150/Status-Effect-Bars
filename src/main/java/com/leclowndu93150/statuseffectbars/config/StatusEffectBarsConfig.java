package com.leclowndu93150.statuseffectbars.config;

import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class StatusEffectBarsConfig {
    public static final ModConfigSpec SPEC;
    public static final StatusEffectBarsConfig INSTANCE;

    static {
        Pair<StatusEffectBarsConfig, ModConfigSpec> pair = new ModConfigSpec.Builder()
                .configure(StatusEffectBarsConfig::new);
        INSTANCE = pair.getLeft();
        SPEC = pair.getRight();
    }

    // Color
    public final ModConfigSpec.EnumValue<ColorMode> colorMode;
    public final ModConfigSpec.IntValue backgroundColor;
    public final ModConfigSpec.IntValue beneficialForegroundColor;
    public final ModConfigSpec.IntValue harmfulForegroundColor;
    public final ModConfigSpec.IntValue neutralForegroundColor;

    // Behavior
    public final ModConfigSpec.IntValue maxRemainingDuration;
    public final ModConfigSpec.IntValue minAmbientAge;

    // Layout
    public final LayoutConfig hudLayout;
    public final LayoutConfig inventoryLayout;

    public StatusEffectBarsConfig(ModConfigSpec.Builder builder) {
        builder.push("color");

        colorMode = builder
                .comment("Color mode for status effect bars",
                        "EFFECT_COLOR: Use the color of the status effect",
                        "CUSTOM: Use custom colors defined below",
                        "CATEGORY: Use colors based on effect category (beneficial/harmful/neutral)")
                .defineEnum("colorMode", ColorMode.EFFECT_COLOR);

        backgroundColor = builder
                .comment("Background color of the bars (ARGB format)")
                .defineInRange("backgroundColor", 0x80000000, Integer.MIN_VALUE, Integer.MAX_VALUE);

        beneficialForegroundColor = builder
                .comment("Foreground color for beneficial effects (ARGB format)")
                .defineInRange("beneficialForegroundColor", 0x80ffffff, Integer.MIN_VALUE, Integer.MAX_VALUE);

        harmfulForegroundColor = builder
                .comment("Foreground color for harmful effects (ARGB format)")
                .defineInRange("harmfulForegroundColor", 0x80ffffff, Integer.MIN_VALUE, Integer.MAX_VALUE);

        neutralForegroundColor = builder
                .comment("Foreground color for neutral effects (ARGB format)")
                .defineInRange("neutralForegroundColor", 0x80ffffff, Integer.MIN_VALUE, Integer.MAX_VALUE);

        builder.pop();

        builder.push("behavior");

        maxRemainingDuration = builder
                .comment("Remaining duration in ticks above which the bar is hidden",
                        "Set to 2147483647 (max int) to never hide bars based on duration")
                .defineInRange("maxRemainingDuration", Integer.MAX_VALUE, 0, Integer.MAX_VALUE);

        minAmbientAge = builder
                .comment("Age in ticks under which the bar of ambient (i.e. beacon) effects is hidden",
                        "Default is 90 ticks (80 + 10)")
                .defineInRange("minAmbientAge", 90, 0, Integer.MAX_VALUE);

        builder.pop();

        builder.push("layout");
        hudLayout = new LayoutConfig(builder, "hud", 3, 2);
        inventoryLayout = new LayoutConfig(builder, "inventory", 4, 3);
        builder.pop();
    }

    public int getColor(MobEffectInstance effect) {
        return colorMode.get().getColor(this, effect);
    }

    public static class LayoutConfig {
        public final ModConfigSpec.BooleanValue enabled;
        public final ModConfigSpec.EnumValue<Direction> direction;
        public final ModConfigSpec.BooleanValue relativeToEnd;
        public final ModConfigSpec.IntValue thickness;
        public final ModConfigSpec.IntValue collinearPadding;
        public final ModConfigSpec.IntValue collinearOffset;
        public final ModConfigSpec.IntValue orthogonalOffset;

        public LayoutConfig(ModConfigSpec.Builder builder, String name, int defaultCollinearPadding, int defaultOrthogonalOffset) {
            builder.push(name);

            enabled = builder
                    .comment("Enable status effect bars in " + name)
                    .define("enabled", true);

            builder.push("position");

            direction = builder
                    .comment("Direction of the bar")
                    .defineEnum("direction", Direction.LEFT_TO_RIGHT);

            relativeToEnd = builder
                    .comment("Whether the bar is placed relative to the end (bottom/right)",
                            "or the start (top/left) of the effect rectangle")
                    .define("relativeToEnd", true);

            builder.pop().push("shape");

            thickness = builder
                    .comment("Thickness of the bar in pixels")
                    .defineInRange("thickness", 1, 1, 10);

            collinearPadding = builder
                    .comment("Padding along the bar's main axis")
                    .defineInRange("collinearPadding", defaultCollinearPadding, 0, 20);

            builder.pop().push("fineTuning");

            collinearOffset = builder
                    .comment("Offset along the bar's main axis")
                    .defineInRange("collinearOffset", 0, -50, 50);

            orthogonalOffset = builder
                    .comment("Offset perpendicular to the bar")
                    .defineInRange("orthogonalOffset", defaultOrthogonalOffset, -50, 50);

            builder.pop().pop();
        }
    }
}