package net.id.incubus_core.mixin.player;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.id.incubus_core.misc.Players.AZZY;
import static net.id.incubus_core.misc.Players.DAF;

@Mixin(PlayerEntity.class)
public abstract class PlayerDropMixin extends LivingEntity {

    @Shadow @Nullable public abstract ItemEntity dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership);

    protected PlayerDropMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "dropInventory", at = @At("HEAD"))
    public void dropEggs(CallbackInfo ci) {
        var uuid = getUuid();
        if(uuid.equals(AZZY)) {
            dropItem(new ItemStack(Items.SWEET_BERRIES), false, false);
        }
        else if(uuid.equals(DAF)) {
            dropItem(new ItemStack(Items.AMETHYST_SHARD), false, false);
        }
    }
}
