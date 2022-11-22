package net.id.incubus_core.recipe.matchbook;

import com.google.gson.JsonObject;
import net.minecraft.network.PacketByteBuf;

import java.util.Optional;

/**
 * A factory, tasked with both creating and configuring Matches, and also building them from packets.
 * Ensure the name matches the registry id.
 */
public abstract class MatchFactory<T extends Match> {

    protected final String name;

    protected MatchFactory(String name) {
        this.name = name;
    }

    public abstract T create(String key, JsonObject object);

    public abstract T fromPacket(PacketByteBuf buf);

    public static Optional<MatchFactory<?>> getForPacket(String name) {
        return MatchRegistry.getOptional(name);
    }

}
