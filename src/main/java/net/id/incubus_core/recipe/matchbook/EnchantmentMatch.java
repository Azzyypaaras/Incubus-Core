package net.id.incubus_core.recipe.matchbook;

import com.google.gson.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;

public class EnchantmentMatch extends Match {

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
    void write(PacketByteBuf buf) {
        buf.writeBoolean(singular);
        buf.writeInt(minLevel);
        buf.writeInt(maxLevel);
        buf.writeString(enchantmentId);
    }

    public static class Factory extends MatchFactory<EnchantmentMatch> {

        public Factory() {
            super("enchantment");
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
