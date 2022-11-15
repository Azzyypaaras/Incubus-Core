package net.id.incubus_core.misc.playerdata;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SpawnItemRegistry {

    private static final List<SpawnItemEntry> SPAWN_ITEMS = new ArrayList<>();

    public static void add(ItemStack stack, boolean firstSpawnOnly) {
        SPAWN_ITEMS.add(new SpawnItemEntry(stack, firstSpawnOnly, playerEntity -> true));
    }

    public static void add(ItemStack stack, boolean firstSpawnOnly, Predicate<PlayerEntity> additionalChecks) {
        SPAWN_ITEMS.add(new SpawnItemEntry(stack, firstSpawnOnly, additionalChecks));
    }

    static List<SpawnItemEntry> getSpawnItems() {
        return SPAWN_ITEMS;
    }

    public record SpawnItemEntry(ItemStack stack, boolean firstSpawnOnly, Predicate<PlayerEntity> additionalChecks) {}
}
