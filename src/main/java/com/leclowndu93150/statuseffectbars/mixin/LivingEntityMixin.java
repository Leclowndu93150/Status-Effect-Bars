package com.leclowndu93150.statuseffectbars.mixin;

import com.leclowndu93150.statuseffectbars.duck.StatusEffectInstanceDuck;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Prevents effects' max duration from being overwritten.
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(
            method = "forceAddEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;onEffectUpdated(Lnet/minecraft/world/effect/MobEffectInstance;ZLnet/minecraft/world/entity/Entity;)V"
            )
    )
    private void onSetStatusEffect(MobEffectInstance instance, Entity entity, CallbackInfo ci, @Local(ordinal = 1) MobEffectInstance mobeffectinstance) {
        if (mobeffectinstance != null && mobeffectinstance.getAmplifier() == instance.getAmplifier()) {
            StatusEffectInstanceDuck duck = (StatusEffectInstanceDuck) instance;
            StatusEffectInstanceDuck oldDuck = (StatusEffectInstanceDuck) mobeffectinstance;
            if (duck.statusEffectBars_getMaxDuration() < oldDuck.statusEffectBars_getMaxDuration()) {
                duck.statusEffectBars_setMaxDuration(
                        oldDuck.statusEffectBars_getMaxDuration()
                );
            }
        }
    }
}