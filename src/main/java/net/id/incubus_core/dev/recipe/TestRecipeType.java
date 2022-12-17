package net.id.incubus_core.dev.recipe;

import com.google.gson.JsonObject;
import net.id.incubus_core.dev.DevInit;
import net.id.incubus_core.json.RecipeParser;
import net.id.incubus_core.recipe.IngredientStack;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class TestRecipeType extends AbstractCookingRecipe {

    private final IngredientStack input;

    public TestRecipeType(Identifier id, IngredientStack input, ItemStack output) {
        super(DevInit.TEST_RECIPE_TYPE, id, "dev", CookingRecipeCategory.MISC, input.getIngredient(), output, 0, 5);
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
            var output = RecipeParser.stackFromJson(json.getAsJsonObject(), "output");

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
