package net.id.incubus_core.recipe.matchbook;

import com.google.gson.*;
import net.id.incubus_core.recipe.RecipeParser;
import net.minecraft.nbt.*;
import net.minecraft.network.*;

public class EnchantmentMatch extends Match {
    public static final String TYPE = "enchantment";

    private boolean singular;
    private int minLevel;
    private int maxLevel;
    private String enchantmentId;

    public EnchantmentMatch(String name, String key) {
        super(name, key);
    }

    @Override
    boolean matches(NbtCompound nbt) {
        if(nbt != null && nbt.contains(key)) {
            if (singular) {

                return testEnchantment(nbt);

            }
            else {

                var success = false;
                var enchants = nbt.getList(key, 10);
                for (int i = 0; i < enchants.size(); i++) {

                    success = testEnchantment(enchants.getCompound(i));

                    if(success) {
                        break;
                    }
                }
                return success;

            }
        }

        return false;
    }

    boolean testEnchantment(NbtCompound nbt) {
        var level = nbt.getShort("lvl");
        var id = nbt.getString("id");

        return id.equals(enchantmentId) && maxLevel >= level && level >= minLevel;
    }

    @Override
    void configure(JsonObject json) {
        singular = json.has("singular") && json.get("singular").getAsBoolean();
        minLevel = json.get("minLevel").getAsInt();
        maxLevel = json.get("maxLevel").getAsInt();
        enchantmentId = json.get("enchantmentId").getAsString();
    }

    @Override
    void configure(PacketByteBuf buf) {
        singular = buf.readBoolean();
        minLevel = buf.readInt();
        maxLevel = buf.readInt();
        enchantmentId = buf.readString();
    }

    @Override
    JsonObject toJson() {
        JsonObject main = new JsonObject();
        main.add(RecipeParser.TYPE, new JsonPrimitive(TYPE));
        main.add(RecipeParser.KEY, new JsonPrimitive(this.name));
        main.add("singular", new JsonPrimitive(singular));
        main.add("minLevel", new JsonPrimitive(minLevel));
        main.add("maxLevel", new JsonPrimitive(maxLevel));
        main.add("enchantmentId", new JsonPrimitive(enchantmentId));
        return main;
    }

    @Override
    void write(PacketByteBuf buf) {
        buf.writeBoolean(singular);
        buf.writeInt(minLevel);
        buf.writeInt(maxLevel);
        buf.writeString(enchantmentId);
    }

    public static class Factory extends MatchFactory<EnchantmentMatch> {

        public Factory() {
            super(TYPE);
        }

        @Override
        public EnchantmentMatch create(String key, JsonObject object) {
            var match = new EnchantmentMatch(name, key);
            match.configure(object);

            return match;
        }

        @Override
        public EnchantmentMatch fromPacket(PacketByteBuf buf) {
            var match = new EnchantmentMatch(name, buf.readString());
            match.configure(buf);

            return match;
        }
    }

}
