package net.id.incubus_core.mixin.entity;

import net.id.incubus_core.misc.item.IncubusCoreItems;
import net.id.incubus_core.util.FoxDuck;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(FoxEntity.class)
public abstract class FoxEditorMixin extends AnimalEntity implements FoxDuck {

    protected FoxEditorMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "canPickupItem", at = @At("HEAD"), cancellable = true)
    public void allowDroppingBerryBranch(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        var heldStack = getEquippedStack(EquipmentSlot.MAINHAND);
        if (heldStack.isOf(IncubusCoreItems.BERRY_BRANCH)) {
            if (stack.isOf(Items.TOTEM_OF_UNDYING) || stack.isOf(Items.ELYTRA) || stack.isOf(Items.ENCHANTED_GOLDEN_APPLE) || stack.isOf(Items.ENDER_EYE)) {
                cir.setReturnValue(true);
                cir.cancel();
            }
        }
    }

    @Shadow public abstract FoxEntity.Type getFoxType();

    @Shadow protected abstract void setType(FoxEntity.Type type);

    @Shadow abstract void addTrustedUuid(@Nullable UUID uuid);

    @Override
    public FoxEntity.Type getFoxColor() {
        return getFoxType();
    }

    @Override
    public void setFoxColor(FoxEntity.Type type) {
        setType(type);
    }

    @Override
    public void addTrustedUUID(UUID id) {
        addTrustedUuid(id);
    }
}
