package net.id.incubus_core.mixin.blocklikeentities;

import net.id.incubus_core.IncubusCore;
import net.id.incubus_core.blocklikeentities.api.BlockLikeEntity;
import net.id.incubus_core.blocklikeentities.api.BlockLikeSet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.EntityList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {

    @Shadow
    @Final
    EntityList entityList;
    @Shadow
    private int idleTimeout;

    @Inject(method = "tick", at = @At(value = "RETURN"))
    void postEntityTick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        if (this.idleTimeout < 300) {
            entityList.forEach(entityObj -> {
                if (entityObj instanceof BlockLikeEntity entity) {
                    entity.postTick();
                } else if (entityObj == null) {
                    IncubusCore.LOG.error("Started checking null entities in ServerWorldMixin::postEntityTick");
                }
            });
            BlockLikeSet.getAllSets().forEachRemaining(BlockLikeSet::postTick);
        }
    }
}
