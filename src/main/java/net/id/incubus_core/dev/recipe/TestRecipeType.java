package net.id.incubus_core.dev.recipe;

import com.google.gson.*;
import net.id.incubus_core.dev.*;
import net.id.incubus_core.recipe.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.recipe.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class TestRecipeType extends AbstractCookingRecipe {

    private final IngredientStack input;

    public TestRecipeType(Identifier id, IngredientStack input, ItemStack output) {
        super(DevInit.TEST_RECIPE_TYPE, id, "dev", input.getIngredient(), output, 0, 5);
        this.input = input;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DevInit.TEST_RECIPE_SERIALIZER;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return input.test(inventory.getStack(0));
    }

    public static class Serializer implements RecipeSerializer<TestRecipeType> {

        @Override
        public TestRecipeType read(Identifier id, JsonObject json) {
            var input = RecipeParser.ingredientStackFromJson(json.getAsJsonObject("input"));
            var output = RecipeParser.getItemStackWithNbtFromJson(JsonHelper.getObject(json, "output"));

            return new TestRecipeType(id, input, output);
        }

        @Override
        public TestRecipeType read(Identifier id, PacketByteBuf buf) {
            var input = IngredientStack.fromByteBuf(buf);
            var output = buf.readItemStack();
            return new TestRecipeType(id, input, output);
        }

        @Override
        public void write(PacketByteBuf buf, TestRecipeType recipe) {
            recipe.input.write(buf);
            buf.writeItemStack(recipe.output);
        }
    }
}
