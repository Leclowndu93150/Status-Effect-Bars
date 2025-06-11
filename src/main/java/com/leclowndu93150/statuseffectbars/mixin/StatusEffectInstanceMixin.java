package com.leclowndu93150.statuseffectbars.mixin;

import com.leclowndu93150.statuseffectbars.duck.StatusEffectInstanceDuck;
import net.minecraft.world.effect.MobEffectInstance;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Adds the {@link #status_Effect_Bars$maxDuration} attribute used for rendering.
 */
@Mixin(MobEffectInstance.class)
public abstract class StatusEffectInstanceMixin implements StatusEffectInstanceDuck {

    @Unique private int status_Effect_Bars$maxDuration;
    @Shadow private int duration;

    @Inject(method = "<init>(Lnet/minecraft/core/Holder;IIZZZLnet/minecraft/world/effect/MobEffectInstance;)V",
            at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        status_Effect_Bars$maxDuration = duration;
    }

    @Inject(method = "setDetailsFrom", at = @At("RETURN"))
    private void onCopyFrom(MobEffectInstance that, CallbackInfo ci) {
        status_Effect_Bars$maxDuration = ((StatusEffectInstanceMixin) (Object) that).status_Effect_Bars$maxDuration;
    }

    @Inject(method = "update", at = @At(value = "FIELD", target = "Lnet/minecraft/world/effect/MobEffectInstance;duration:I", opcode = Opcodes.PUTFIELD))
    private void onUpgrade(MobEffectInstance that, CallbackInfoReturnable<Boolean> cir) {
        status_Effect_Bars$maxDuration = ((StatusEffectInstanceMixin) (Object) that).status_Effect_Bars$maxDuration;
    }

    @Override
    public int statusEffectBars_getMaxDuration() {
        return status_Effect_Bars$maxDuration;
    }

    @Override
    public void statusEffectBars_setMaxDuration(int maxDuration) {
        this.status_Effect_Bars$maxDuration = maxDuration;
    }
}