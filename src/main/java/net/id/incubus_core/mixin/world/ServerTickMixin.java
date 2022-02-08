package net.id.incubus_core.mixin.world;

import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public class ServerTickMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {

    }
}
