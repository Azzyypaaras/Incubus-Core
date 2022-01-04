package net.id.incubus_core.systems;

import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.id.incubus_core.IncubusCore;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.id.incubus_core.IncubusCore.locate;

@SuppressWarnings("unused")
public class Lookups {
    public static final BlockApiLookup<HeatIo, @NotNull Direction> HEAT =
            BlockApiLookup.get(locate("heat_system_b"), HeatIo.class, Direction.class);

    public static final BlockApiLookup<KineticIo, @NotNull Direction> KINETIC =
            BlockApiLookup.get(locate("kinetic_system_b"), KineticIo.class, Direction.class);

    public static final BlockApiLookup<PressureIo, @NotNull Direction> PRESSURE =
            BlockApiLookup.get(locate("pressure_system_b"), PressureIo.class, Direction.class);

    public static final BlockApiLookup<MaterialProvider, @Nullable Void> MATERIAL =
            BlockApiLookup.get(locate("material_lookup_b"), MaterialProvider.class, Void.class);


    public static final ItemApiLookup<HeatIo, @Nullable Void> ITEM_HEAT =
            ItemApiLookup.get(locate("heat_system_i"), HeatIo.class, Void.class);

    public static final ItemApiLookup<PressureIo, @Nullable Void> ITEM_PRESSURE =
            ItemApiLookup.get(locate("pressure_system_i"), PressureIo.class, Void.class);

    public static final ItemApiLookup<MaterialProvider, @Nullable Void> ITEM_MATERIAL =
            ItemApiLookup.get(locate("material_lookup_i"), MaterialProvider.class, Void.class);


    public static <T> @Nullable T ofPlayerHand(ItemApiLookup<T, Void> lookup, PlayerEntity player, Hand hand) {
        return lookup.find(player.getStackInHand(hand), null);
    }

    public static <T> @Nullable T ofPlayerCursor(ItemApiLookup<T, Void> lookup, ScreenHandler screenHandler) {
        return lookup.find(screenHandler.getCursorStack(), null);
    }


    public static long pressureInverse(double pressure) {
        if(pressure <= 0) {
            return 0;
        }
        return (long) Math.max(0, (Math.sqrt(pressure) * 36586.544243));
    }

    public static double accelerationFromPressureGradient(double self, double other) {
        return Math.sqrt(Math.abs(self - other)) * ((self < other) ? -1 : 1);
    }
}
