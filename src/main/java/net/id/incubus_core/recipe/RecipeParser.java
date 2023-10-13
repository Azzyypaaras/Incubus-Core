package net.id.incubus_core.recipe;

import com.google.gson.*;
import com.google.gson.stream.*;
import com.mojang.brigadier.exceptions.*;
import net.id.incubus_core.*;
import net.id.incubus_core.recipe.matchbook.*;
import net.id.incubus_core.util.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;
import net.minecraft.util.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

@SuppressWarnings("unused")
public class RecipeParser {

    private static final JsonParser PARSER = new JsonParser();
    public static final String ITEM = "item";
    public static final String COUNT = "count";
    public static final String KEY = "key";
    public static final String MATCHBOOK = "matchbook";
    public static final String MAX = "max";
    public static final String MIN = "min";
    public static final String TARGET = "target";
    public static final String TYPE = "type";

    public static JsonObject fromInputStream(InputStream in) {
        return JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
    }

    public static ItemStack stackFromJson(JsonObject json, String elementName) {
        Item item = Registries.ITEM.get(Identifier.tryParse(json.get(elementName).getAsString()));
        int count = json.has(COUNT) ? json.get("count").getAsInt() : 1;
        return item != Items.AIR ? new ItemStack(item, count) : ItemStack.EMPTY;
    }

    public static ItemStack stackFromJson(JsonObject json) {
        return stackFromJson(json, ITEM);
    }

    public static IngredientStack ingredientStackFromJson(JsonObject json) {
        Ingredient ingredient = json.has("ingredient") ? Ingredient.fromJson(json.getAsJsonObject("ingredient")) : Ingredient.fromJson(json);
        var matchbook = Matchbook.empty();
        NbtCompound recipeViewNbt = null;
        int count = json.has(COUNT) ? json.get(COUNT).getAsInt() : 1;

        if (json.has(MATCHBOOK)) {
            try {
                matchbook = matchbookFromJson(json.getAsJsonObject(MATCHBOOK));
            } catch (MalformedJsonException e) {
                IncubusCore.LOG.error("RELAYED EXCEPTION. " + e);
            }
        }

        if (json.has("recipeViewNbt")) {
            try {
                recipeViewNbt = NbtHelper.fromNbtProviderString(json.get("recipeViewNbt").getAsString());
            } catch (CommandSyntaxException e) {
                IncubusCore.LOG.error("RELAYED EXCEPTION. " + e);
            }
        }

        return IngredientStack.of(ingredient, matchbook, recipeViewNbt, count);
    }

    public static List<IngredientStack> ingredientStacksFromJson(JsonArray array, int size) {
         List<IngredientStack> ingredients = new ArrayList<>(size);
         int dif = size - array.size();
        for (int i = 0; i < array.size() && i < size; i++) {
            JsonObject object = array.get(i).getAsJsonObject();
            ingredients.add(ingredientStackFromJson(object));
        }
        if(dif > 0) {
            for (int i = 0; i < dif; i++) {
                ingredients.add(IngredientStack.EMPTY);
            }
        }
        return ingredients;
    }

    public static OptionalStack optionalStackFromJson(JsonObject json) throws MalformedJsonException {
        int count = json.has(COUNT) ? json.get(COUNT).getAsInt() : 1;
        if(json.has(ITEM)) {
            Item item = Registries.ITEM.get(Identifier.tryParse(json.get("item").getAsString()));
            return item != Items.AIR ? new OptionalStack(new ItemStack(item, count), count) : OptionalStack.EMPTY;
        }
        else if(json.has("tag")) {
            var tagId = Identifier.tryParse(json.get("tag").getAsString());
            var tag = TagKey.of(Registries.ITEM.getKey(), tagId);
            return !RegistryHelper.isTagEmpty(tag) ? new OptionalStack(tag, count) : OptionalStack.EMPTY;
        }
        else {
            throw new MalformedJsonException("OptionalStacks must have an item or tag!");
        }
    }

    public static List<OptionalStack> optionalStacksFromJson(JsonArray array, int size) throws MalformedJsonException {
        List<OptionalStack> stacks = new ArrayList<>(size);
        int dif = size - array.size();
        for (int i = 0; i < array.size() && i < size; i++) {
            JsonObject object = array.get(i).getAsJsonObject();
            stacks.add(optionalStackFromJson(object));
        }
        if(dif > 0) {
            for (int i = 0; i < dif; i++) {
                stacks.add(OptionalStack.EMPTY);
            }
        }
        return stacks;
    }

    public static Matchbook matchbookFromJson(JsonObject json) throws MalformedJsonException {
        var builder = new Matchbook.Builder();
        var matchArray = json.getAsJsonArray("matches");

        var mode = Matchbook.Mode.valueOf(json.get("mode").getAsString());

        for (int i = 0; i < matchArray.size(); i++) {
            var entry = matchArray.get(i).getAsJsonObject();
            var id = entry.get(TYPE).getAsString();
            var key = entry.get(KEY).getAsString();

            var optional = MatchRegistry.getOptional(id);
            if(optional.isEmpty()) {
                throw new MalformedJsonException("Invalid Match Type at index " + i);
            }

            var factory = optional.get();

            builder.add(factory.create(key, entry));
        }

        return builder.build(mode);
    }

    /**
     * Parses an ItemStack json object with optional NBT data
     * Can be used in RecipeSerializers to get ItemStacks with NBT as output
     * @param json The JsonObject to parse
     * @return An ItemStack with nbt data, like specified in the json
     */
    public static ItemStack getItemStackWithNbtFromJson(JsonObject json) {
        String string = JsonHelper.getString(json, "item");
        Item item = Registries.ITEM.getOrEmpty(new Identifier(string)).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + string + "'"));
        if (item == Items.AIR) {
            throw new JsonSyntaxException("Invalid item: " + string);
        }
        
        if (json.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        }

        int count = JsonHelper.getInt(json, COUNT, 1);
        if (count < 1) {
            throw new JsonSyntaxException("Invalid output count: " + count);
        }

        ItemStack stack = new ItemStack(item, count);
        String nbt = JsonHelper.getString(json, "nbt", "");
        if(nbt.isEmpty()) {
            return stack;
        }

        try {
            NbtCompound compound = NbtHelper.fromNbtProviderString(nbt);
            compound.remove("palette");
            stack.setNbt(compound);
        } catch (CommandSyntaxException e) {
            throw new JsonSyntaxException("Invalid output nbt: " + nbt);
        }

        return stack;
    }

    public static JsonElement asJson(NbtElement nbt) {
        if (nbt == null) {
            return JsonNull.INSTANCE;
        }
        if (nbt instanceof NbtString s) return new JsonPrimitive(s.asString());
        if (nbt instanceof NbtByte b) return new JsonPrimitive(b.byteValue() == 1);
        if (nbt instanceof AbstractNbtNumber n) return new JsonPrimitive(n.numberValue());
        if (nbt instanceof AbstractNbtList<?> l) {
            JsonArray arr =  new JsonArray();
            l.stream().map(RecipeParser::asJson).forEach(arr::add);
            return arr;
        }
        if (nbt instanceof NbtCompound c) {
            JsonObject o = new JsonObject();
            c.getKeys().forEach(k -> o.add(k, asJson(c.get(k))));
            return o;
        }
        return null;
    }
}
