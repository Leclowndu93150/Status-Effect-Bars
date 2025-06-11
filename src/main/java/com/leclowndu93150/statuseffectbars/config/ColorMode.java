package com.leclowndu93150.statuseffectbars.config;


import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.function.ToIntBiFunction;

public enum ColorMode {
    EFFECT_COLOR(
            (config, effect) -> effect.getEffect().getColor() | 0xff000000
    ),
    @SuppressWarnings({"ConstantConditions", "unused"})
    CATEGORY_COLOR(
            (config, effect) -> effect.getEffect().getCategory().getTooltipFormatting().getColor() | 0xff000000
    ),
    @SuppressWarnings("unused") CUSTOM(
            (config, effect) -> switch (effect.getEffect().getCategory()) {
                case BENEFICIAL -> config.beneficialForegroundColor.get();
                case HARMFUL -> config.harmfulForegroundColor.get();
                default -> config.neutralForegroundColor.get();
            }
    );

    private final ToIntBiFunction<StatusEffectBarsConfig, MobEffectInstance> colorProvider;

    ColorMode(ToIntBiFunction<StatusEffectBarsConfig, MobEffectInstance> colorProvider) {
        this.colorProvider = colorProvider;
    }

    public int getColor(StatusEffectBarsConfig config, MobEffectInstance effect) {
        return colorProvider.applyAsInt(config, effect);
    }

    @Override
    public String toString() {
        return I18n.get("text.autoconfig.status-effect-bars.option.colorMode." + name());
    }

}