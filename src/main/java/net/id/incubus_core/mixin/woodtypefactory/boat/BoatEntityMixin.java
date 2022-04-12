package net.id.incubus_core.mixin.woodtypefactory.boat;

import net.id.incubus_core.woodtypefactory.api.boat.BoatFactory;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin {
    @Shadow
    public abstract BoatEntity.Type getBoatType();

    @Inject(method = "asItem", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;OAK_BOAT:Lnet/minecraft/item/Item;", opcode = Opcodes.GETSTATIC), cancellable = true)
    private void checkAetherBoats(CallbackInfoReturnable<Item> cir) {
        BoatEntity.Type type = this.getBoatType();
        if (type != BoatEntity.Type.OAK) {
            for (var entry : BoatFactory.BOAT_FACTORIES){
                if (type == entry.boatType){
                    cir.setReturnValue(entry.item);
                }
            }
        }
    }
}
