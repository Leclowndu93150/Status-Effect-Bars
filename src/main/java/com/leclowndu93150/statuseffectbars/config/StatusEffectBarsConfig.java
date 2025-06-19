package com.leclowndu93150.statuseffectbars.config;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class StatusEffectBarsConfig {
    public static final ForgeConfigSpec SPEC;
    public static final StatusEffectBarsConfig INSTANCE;

    static {
        Pair<StatusEffectBarsConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder()
                .configure(StatusEffectBarsConfig::new);
        INSTANCE = pair.getLeft();
        SPEC = pair.getRight();
    }

    // Color
    public final ForgeConfigSpec.EnumValue<ColorMode> colorMode;
    public final ForgeConfigSpec.IntValue backgroundColor;
    public final ForgeConfigSpec.IntValue beneficialForegroundColor;
    public final ForgeConfigSpec.IntValue harmfulForegroundColor;
    public final ForgeConfigSpec.IntValue neutralForegroundColor;

    // Behavior
    public final ForgeConfigSpec.IntValue maxRemainingDuration;
    public final ForgeConfigSpec.IntValue minAmbientAge;
    public final ForgeConfigSpec.BooleanValue renderOldTimer;
    public final ForgeConfigSpec.BooleanValue renderCustomTimer;

    // Layout
    public final LayoutConfig hudLayout;
    public final LayoutConfig inventoryLayout;

    public StatusEffectBarsConfig(ForgeConfigSpec.Builder builder) {
        builder.push("color");

        colorMode = builder
                .comment("Color mode for status effect bars",
                        "EFFECT_COLOR: Use the color of the status effect",
                        "CUSTOM: Use custom colors defined below",
                        "CATEGORY: Use colors based on effect category (beneficial/harmful/neutral)")
                .translation("statuseffectbars.configuration.colorMode")
                .defineEnum("colorMode", ColorMode.EFFECT_COLOR);

        backgroundColor = builder
                .comment("Background color of the bars (ARGB format)")
                .translation("statuseffectbars.configuration.backgroundColor")
                .defineInRange("backgroundColor", 0x80000000, Integer.MIN_VALUE, Integer.MAX_VALUE);

        beneficialForegroundColor = builder
                .comment("Foreground color for beneficial effects (ARGB format)")
                .translation("statuseffectbars.configuration.beneficialForegroundColor")
                .defineInRange("beneficialForegroundColor", 0x80ffffff, Integer.MIN_VALUE, Integer.MAX_VALUE);

        harmfulForegroundColor = builder
                .comment("Foreground color for harmful effects (ARGB format)")
                .translation("statuseffectbars.configuration.harmfulForegroundColor")
                .defineInRange("harmfulForegroundColor", 0x80ffffff, Integer.MIN_VALUE, Integer.MAX_VALUE);

        neutralForegroundColor = builder
                .comment("Foreground color for neutral effects (ARGB format)")
                .translation("statuseffectbars.configuration.neutralForegroundColor")
                .defineInRange("neutralForegroundColor", 0x80ffffff, Integer.MIN_VALUE, Integer.MAX_VALUE);

        builder.pop();

        builder.push("behavior");

        maxRemainingDuration = builder
                .comment("Remaining duration in ticks above which the bar is hidden",
                        "Set to 2147483647 (max int) to never hide bars based on duration")
                .translation("statuseffectbars.configuration.maxRemainingDuration")
                .defineInRange("maxRemainingDuration", Integer.MAX_VALUE, 0, Integer.MAX_VALUE);

        minAmbientAge = builder
                .comment("Age in ticks under which the bar of ambient (i.e. beacon) effects is hidden",
                        "Default is 90 ticks (80 + 10)")
                .translation("statuseffectbars.configuration.minAmbientAge")
                .defineInRange("minAmbientAge", 90, 0, Integer.MAX_VALUE);

        renderOldTimer = builder
                .comment("Render the original Stylish Effects timer",
                        "When false, the original timer is hidden")
                .translation("statuseffectbars.configuration.renderOldTimer")
                .define("renderOldTimer", false);

        renderCustomTimer = builder
                .comment("Render the custom timer for status effect bars when Stylish Effects is installed.",
                        "When true, renders a smaller duration text at the top left of effects")
                .translation("statuseffectbars.configuration.renderCustomTimer")
                .define("renderCustomTimer", true);

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
        public final ForgeConfigSpec.BooleanValue enabled;
        public final ForgeConfigSpec.EnumValue<Direction> direction;
        public final ForgeConfigSpec.BooleanValue relativeToEnd;
        public final ForgeConfigSpec.IntValue thickness;
        public final ForgeConfigSpec.IntValue collinearPadding;
        public final ForgeConfigSpec.IntValue collinearOffset;
        public final ForgeConfigSpec.IntValue orthogonalOffset;

        public LayoutConfig(ForgeConfigSpec.Builder builder, String name, int defaultCollinearPadding, int defaultOrthogonalOffset) {
            builder.push(name);

            enabled = builder
                    .comment("Enable status effect bars in " + name)
                    .translation("statuseffectbars.configuration." + name + ".enabled")
                    .define("enabled", true);

            builder.push("position");

            direction = builder
                    .comment("Direction of the bar")
                    .translation("statuseffectbars.configuration." + name + ".direction")
                    .defineEnum("direction", Direction.LEFT_TO_RIGHT);

            relativeToEnd = builder
                    .comment("Whether the bar is placed relative to the end (bottom/right)",
                            "or the start (top/left) of the effect rectangle")
                    .translation("statuseffectbars.configuration." + name + ".relativeToEnd")
                    .define("relativeToEnd", true);

            builder.pop().push("shape");

            thickness = builder
                    .comment("Thickness of the bar in pixels")
                    .translation("statuseffectbars.configuration." + name + ".thickness")
                    .defineInRange("thickness", 1, 1, 10);

            collinearPadding = builder
                    .comment("Padding along the bar's main axis")
                    .translation("statuseffectbars.configuration." + name + ".collinearPadding")
                    .defineInRange("collinearPadding", defaultCollinearPadding, 0, 20);

            builder.pop().push("fineTuning");

            collinearOffset = builder
                    .comment("Offset along the bar's main axis")
                    .translation("statuseffectbars.configuration." + name + ".collinearOffset")
                    .defineInRange("collinearOffset", 0, -50, 50);

            orthogonalOffset = builder
                    .comment("Offset perpendicular to the bar")
                    .translation("statuseffectbars.configuration." + name + ".orthogonalOffset")
                    .defineInRange("orthogonalOffset", defaultOrthogonalOffset, -50, 50);

            builder.pop().pop();
        }
    }
}