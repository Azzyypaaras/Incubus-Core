package net.id.incubus_core.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.MalformedJsonException;
import net.id.incubus_core.IncubusCore;
import net.id.incubus_core.recipe.IngredientStack;
//import net.id.incubus_core.recipe.OptionalStack;
import net.id.incubus_core.recipe.OptionalStack;
import net.id.incubus_core.recipe.matchbook.MatchRegistry;
import net.id.incubus_core.recipe.matchbook.Matchbook;
import net.id.incubus_core.util.RegistryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
//import net.minecraft.tag.ServerTagManagerHolder;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class JsonUtils {

    private static final JsonParser PARSER = new JsonParser();

    public static JsonObject fromInputStream(InputStream in) {
        return PARSER.parse(new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8))).getAsJsonObject();
    }

    public static ItemStack stackFromJson(JsonObject json) {
        Item item = Registry.ITEM.get(Identifier.tryParse(json.get("item").getAsString()));
        int count = json.has("count") ? json.get("count").getAsInt() : 1;
        return item != Items.AIR ? new ItemStack(item, count) : ItemStack.EMPTY;
    }

    public static IngredientStack ingredientFromJson(JsonObject json) {
        Ingredient ingredient = Ingredient.fromJson(json.get("ingredient"));
        var matchbook = Matchbook.empty();
        int count = json.has("count") ? json.get("count").getAsInt() : 1;

        if (json.has("matchbook")) {
            try {
                matchbook = matchbookFromJson(json.getAsJsonObject("matchbook"));
            } catch (MalformedJsonException e) {
                IncubusCore.LOG.error("RELAYED EXCEPTION. " + e);
            }
        }

        return IngredientStack.of(ingredient, matchbook, count);
    }

    public static List<IngredientStack> ingredientsFromJson(JsonArray array, int size) {
         List<IngredientStack> ingredients = new ArrayList<>(size);
         int dif = size - array.size();
        for (int i = 0; i < array.size() && i < size; i++) {
            JsonObject object = array.get(i).getAsJsonObject();
            ingredients.add(ingredientFromJson(object));
        }
        if(dif > 0) {
            for (int i = 0; i < dif; i++) {
                ingredients.add(IngredientStack.EMPTY);
            }
        }
        return ingredients;
    }

    public static OptionalStack optionalStackFromJson(JsonObject json) throws MalformedJsonException {
        int count = json.has("count") ? json.get("count").getAsInt() : 1;
        if(json.has("item")) {
            Item item = Registry.ITEM.get(Identifier.tryParse(json.get("item").getAsString()));
            return item != Items.AIR ? new OptionalStack(new ItemStack(item, count), count) : OptionalStack.EMPTY;
        }
        else if(json.has("tag")) {
            var tagId = Identifier.tryParse(json.get("tag").getAsString());
            var tag = TagKey.of(Registry.ITEM.getKey(), tagId);
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
            var id = entry.get("type").getAsString();
            var key = entry.get("key").getAsString();

            var optional = MatchRegistry.getOptional(id);
            if(optional.isEmpty()) {
                throw new MalformedJsonException("Invalid Match Type at index " + i);
            }

            var factory = optional.get();

            builder.add(factory.create(key, entry));
        }

        return builder.build(mode);
    }

}
