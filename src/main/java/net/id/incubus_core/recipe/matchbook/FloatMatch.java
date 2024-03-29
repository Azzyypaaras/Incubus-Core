package net.id.incubus_core.recipe.matchbook;

import com.google.gson.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;

/**
 * Exclusive.
 */
public class FloatMatch extends Match {

    private float min;
    private float max;

    public FloatMatch(String name, String key) {
        super(name, key);
    }

    @Override
    boolean matches(NbtCompound nbt) {
        if(nbt != null && nbt.contains(key)) {
            var testFloat = nbt.getFloat(key);
            return max > testFloat && testFloat > min;
        }

        return false;
    }

    @Override
    void configure(JsonObject json) {
        min = json.get("min").getAsFloat();
        min = json.get("max").getAsFloat();
    }

    @Override
    void configure(PacketByteBuf buf) {
        min = buf.readFloat();
        max = buf.readFloat();
    }

    @Override
    void write(PacketByteBuf buf) {
        buf.writeFloat(min);
        buf.writeFloat(max);
    }

    public static class Factory extends MatchFactory<FloatMatch> {

        public Factory() {
            super("float");
        }

        @Override
        public FloatMatch create(String key, JsonObject object) {
            var match = new FloatMatch(name, key);
            match.configure(object);

            return match;
        }

        @Override
        public FloatMatch fromPacket(PacketByteBuf buf) {
            var match = new FloatMatch(name, buf.readString());
            match.configure(buf);

            return match;
        }
    }

}
