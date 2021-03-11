package azzy.fabric.incubus_core.json;

import azzy.fabric.incubus_core.recipe.IngredientStack;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
        int count = json.has("count") ? json.get("count").getAsInt() : 1;
        return IngredientStack.of(ingredient, count);
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
}
