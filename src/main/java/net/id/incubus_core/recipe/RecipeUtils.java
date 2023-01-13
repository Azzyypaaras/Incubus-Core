package net.id.incubus_core.recipe;

import com.google.gson.*;
import com.mojang.brigadier.exceptions.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.recipe.*;
import net.minecraft.util.*;

public class RecipeUtils {

    /**
     * Parses an ItemStack json object with optional NBT data
     * Can be used in RecipeSerializers to get ItemStacks with NBT as output
     * @param json The JsonObject to parse
     * @return An ItemStack with nbt data, like specified in the json
     */
    public static ItemStack getItemStackWithNbtFromJson(JsonObject json) {
        Item item = ShapedRecipe.getItem(json);
        if (json.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            int count = JsonHelper.getInt(json, "count", 1);
            if (count < 1) {
                throw new JsonSyntaxException("Invalid output count: " + count);
            } else {
                ItemStack stack = new ItemStack(item, count);
                String nbt = JsonHelper.getString(json, "nbt", "");
                if (!nbt.isEmpty()) {
                    try {
                        NbtCompound compound = NbtHelper.fromNbtProviderString(nbt);
                        compound.remove("palette");
                        stack.setNbt(compound);
                    } catch (CommandSyntaxException e) {
                        throw new JsonSyntaxException("Invalid output nbt: " + nbt);
                    }
                }

                return stack;
            }
        }
    }

}
