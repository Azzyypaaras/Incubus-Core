package net.id.incubus_core.woodtypefactory.api.boat;

import net.id.incubus_core.util.EnumExtender;
import net.minecraft.block.Block;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BoatItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * A class that handles the creation of boats.
 */
@SuppressWarnings("unused")
public class BoatFactory {
    public static final Set<BoatFactory> BOAT_FACTORIES = new HashSet<>();

    public final BoatItem item;
    public final BoatEntity.Type boatType;

    /**
     * Creates a new boat factory, and registers everything so you don't have to.
     */
    public BoatFactory(Item.Settings itemSettings, Block rootBlock, String modId, String boatName) {
        String boatId = (modId + "_" + boatName);

        this.boatType = EnumExtender.add(BoatEntity.Type.class, boatId.toUpperCase(Locale.ROOT), rootBlock, boatId);

        this.item = new BoatItem(this.boatType, itemSettings);
        Registry.register(Registry.ITEM, new Identifier(modId, boatName + "_boat"), this.item);

        BOAT_FACTORIES.add(this);
    }
}
