package net.id.incubus_core.recipe.matchbook;

import com.google.gson.*;
import net.id.incubus_core.recipe.RecipeParser;
import net.minecraft.nbt.*;
import net.minecraft.network.*;

public class ShortMatch extends Match {
    public static final String TYPE = "short";

    private short targetShort;

    public ShortMatch(String name, String key) {
        super(name, key);
    }

    @Override
    boolean matches(NbtCompound nbt) {
        if(nbt != null && nbt.contains(key)) {
            return nbt.getShort(key) == targetShort;
        }

        return false;
    }

    @Override
    void configure(JsonObject json) {
        targetShort = json.get(RecipeParser.TARGET).getAsShort();
    }

    @Override
    void configure(PacketByteBuf buf) {
        targetShort = buf.readShort();
    }

    @Override
    JsonObject toJson() {
        JsonObject main = new JsonObject();
        main.add(RecipeParser.TYPE, new JsonPrimitive(TYPE));
        main.add(RecipeParser.KEY, new JsonPrimitive(this.name));
        main.add(RecipeParser.TARGET, new JsonPrimitive(targetShort));
        return main;
    }

    @Override
    void write(PacketByteBuf buf) {
        buf.writeShort(targetShort);
    }

    public static class Factory extends MatchFactory<ShortMatch> {

        public Factory() {
            super(TYPE);
        }

        @Override
        public ShortMatch create(String key, JsonObject object) {
            var match = new ShortMatch(name, key);
            match.configure(object);

            return match;
        }

        @Override
        public ShortMatch fromPacket(PacketByteBuf buf) {
            var match = new ShortMatch(name, buf.readString());
            match.configure(buf);

            return match;
        }
    }

}
