package net.id.incubus_core.recipe.matchbook;

import com.google.gson.JsonObject;
import net.minecraft.network.PacketByteBuf;

/**
 * A factory, tasked with both creating and configuring Matches, and also building them from packets.
 * Ensure the name matches the registry id.
 */
public class MatchFactory<T extends Match> {

    protected final String name;

    protected MatchFactory(String name) {
        this.name = name;
    }

    public T create(String key, JsonObject object) {
        return null;
    }

    public T fromPacket(PacketByteBuf buf) {
        return null;
    }

    public static MatchFactory<?> getForPacket(String name) {
        return MatchRegistry.getOptional(name).get();
    }

}
