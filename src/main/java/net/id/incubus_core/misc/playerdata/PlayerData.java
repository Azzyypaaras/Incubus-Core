package net.id.incubus_core.misc.playerdata;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class PlayerData implements AutoSyncedComponent, ServerTickingComponent, PlayerComponent<PlayerData> {

    private final PlayerEntity player;

    private boolean firstSpawn = true;
    private boolean giveSpawnItems = true;
    private boolean deemedWorthy = false;
    private boolean blockRendering = false;

    private int parryStateTicks = 0;

    public PlayerData(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void serverTick() {
        if (parryStateTicks > 0)
            parryStateTicks--;

        if (giveSpawnItems) {
            giveSpawnItems = false;

            SpawnItemRegistry.getSpawnItems()
                    .stream()
                    .filter(entry -> !entry.firstSpawnOnly() || firstSpawn)
                    .filter(entry -> entry.additionalChecks().test(player))
                    .map(entry -> entry.stack().copy())
                    .forEach(player::giveItemStack);

            if (firstSpawn) {
                firstSpawn = false;
            }
        }
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        firstSpawn = tag.getBoolean("firstSpawn");
        giveSpawnItems = tag.getBoolean("giveSpawnItems");
        deemedWorthy = tag.getBoolean("deemedWorthy;");
        blockRendering = tag.getBoolean("blockRendering");
        parryStateTicks = tag.getInt("parryStateTicks");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("firstSpawn", firstSpawn);
        tag.putBoolean("giveSpawnItems", giveSpawnItems);
        tag.putBoolean("deemedWorthy", deemedWorthy);
        tag.putBoolean("blockRendering", blockRendering);
        tag.putInt("parryStateTicks", parryStateTicks);
    }

    public boolean shouldSkipRender() {
        return blockRendering;
    }

    public void setBlockRendering(boolean blockRendering) {
        this.blockRendering = blockRendering;
    }

    public boolean isDeemedWorthy() {
        return deemedWorthy;
    }

    public void deemWorthy(boolean worthy) {
        this.deemedWorthy = worthy;
    }

    public int getParryStateTicks() {
        return parryStateTicks;
    }

    public void setParryStateTicks(int ticks) {
        parryStateTicks = ticks;
    }

    @Override
    public boolean shouldCopyForRespawn(boolean lossless, boolean keepInventory, boolean sameCharacter) {
        return true;
    }

    @Override
    public void copyForRespawn(PlayerData original, boolean lossless, boolean keepInventory, boolean sameCharacter) {
        PlayerComponent.super.copyForRespawn(original, lossless, keepInventory, sameCharacter);
        giveSpawnItems = !lossless && !keepInventory;
        parryStateTicks = !lossless ? 0 : original.parryStateTicks;
    }
}
