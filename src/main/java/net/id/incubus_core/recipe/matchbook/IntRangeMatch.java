package net.id.incubus_core.recipe.matchbook;

import com.google.gson.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;

/**
 * Inclusive.
 */
public class IntRangeMatch extends Match {

    private int min;
    private int max;

    public IntRangeMatch(String name, String key) {
        super(name, key);
    }

    @Override
    boolean matches(NbtCompound nbt) {
        if(nbt != null && nbt.contains(key)) {
            var testInt = nbt.getInt(key);
            return max >= testInt && testInt >= min;
        }

        return false;
    }

    @Override
    void configure(JsonObject json) {
        min = json.get("min").getAsInt();
        min = json.get("max").getAsInt();
    }

    @Override
    void configure(PacketByteBuf buf) {
        min = buf.readInt();
        max = buf.readInt();
    }

    @Override
    void write(PacketByteBuf buf) {
        buf.writeInt(min);
        buf.writeInt(max);
    }

    public static class Factory extends MatchFactory<IntRangeMatch> {

        public Factory() {
            super("intRange");
        }

        @Override
        public IntRangeMatch create(String key, JsonObject object) {
            var match = new IntRangeMatch(name, key);
            match.configure(object);

            return match;
        }

        @Override
        public IntRangeMatch fromPacket(PacketByteBuf buf) {
            var match = new IntRangeMatch(name, buf.readString());
            match.configure(buf);

            return match;
        }
    }

}
