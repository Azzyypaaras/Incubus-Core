package net.id.incubus_core.recipe.matchbook;

import com.google.gson.*;
import net.id.incubus_core.recipe.RecipeParser;
import net.minecraft.nbt.*;
import net.minecraft.network.*;

public class LongMatch extends Match {
    public static final String TYPE = "long";

    private long targetLong;

    public LongMatch(String name, String key) {
        super(name, key);
    }

    @Override
    boolean matches(NbtCompound nbt) {
        if(nbt != null && nbt.contains(key)) {
            return nbt.getLong(key) == targetLong;
        }

        return false;
    }

    @Override
    void configure(JsonObject json) {
        targetLong = json.get(RecipeParser.TARGET).getAsLong();
    }

    @Override
    void configure(PacketByteBuf buf) {
        targetLong = buf.readLong();
    }

    @Override
    JsonObject toJson() {
        JsonObject main = new JsonObject();
        main.add("type", new JsonPrimitive(TYPE));
        main.add(RecipeParser.TARGET, new JsonPrimitive(targetLong));
        return main;
    }

    @Override
    void write(PacketByteBuf buf) {
        buf.writeLong(targetLong);
    }

    public static class Factory extends MatchFactory<LongMatch> {

        public Factory() {
            super(TYPE);
        }

        @Override
        public LongMatch create(String key, JsonObject object) {
            var match = new LongMatch(name, key);
            match.configure(object);

            return match;
        }

        @Override
        public LongMatch fromPacket(PacketByteBuf buf) {
            var match = new LongMatch(name, buf.readString());
            match.configure(buf);

            return match;
        }
    }

}
