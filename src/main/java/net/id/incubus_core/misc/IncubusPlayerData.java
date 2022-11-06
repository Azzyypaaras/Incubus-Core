package net.id.incubus_core.misc;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.id.incubus_core.IncubusCore;
import net.id.incubus_core.condition.base.ConditionCommand;
import net.id.incubus_core.misc.item.IncubusCoreItems;
import net.id.incubus_core.misc.playerdata.PlayerData;
import net.id.incubus_core.misc.playerdata.SpawnItemRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static net.id.incubus_core.misc.Players.AZZY;
import static net.id.incubus_core.misc.Players.PIE;

@ApiStatus.Internal
public final class IncubusPlayerData implements EntityComponentInitializer {

    public static final ComponentKey<PlayerData> PLAYER_DATA_KEY = ComponentRegistry.getOrCreate(IncubusCore.locate("player_data"), PlayerData.class);

    // Tomfoolery
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(PlayerEntity.class, PLAYER_DATA_KEY, PlayerData::new);
    }


    public static void init() {
        SpawnItemRegistry.add(new ItemStack(IncubusCoreItems.AZZYS_ELEMENTAL_FLAG_ITEM), false, playerEntity -> playerEntity.getUuid().equals(AZZY));
        SpawnItemRegistry.add(new ItemStack(IncubusCoreItems.LUNARIAN_SABER_ITEM), false, playerEntity -> playerEntity.getUuid().equals(AZZY));
        SpawnItemRegistry.add(new ItemStack(IncubusCoreItems.LONG_SPATULA), false, playerEntity -> playerEntity.getUuid().equals(PIE));
    }

    public static PlayerData get(@NotNull PlayerEntity player) {
        return PLAYER_DATA_KEY.get(player);
    }
}
