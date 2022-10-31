package net.id.incubus_core.recipe.matchbook;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;

/**
 * Tests ItemStack nbt against a set of rules. Defined by json, must be able to be loaded from a bytebuf.
 */
public abstract class Match {

    protected final String name;
    protected final String key;

    protected Match(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    abstract boolean matches(ItemStack stack);

    abstract void configure(JsonObject json);

    abstract void configure(PacketByteBuf buf);

    public void writeInternal(PacketByteBuf buf) {
        buf.writeString(name);
        write(buf);
    }

    abstract void write(PacketByteBuf buf);

}
