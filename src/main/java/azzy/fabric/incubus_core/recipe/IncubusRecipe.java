package azzy.fabric.incubus_core.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.registry.Registry;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public interface IncubusRecipe<C extends Inventory> extends Recipe<C> {

    boolean isEmpty();

    @Override
    IncubusRecipeType<?> getType();

    List<IngredientStack> getInputs();

    @Override
    default String getGroup() {
        return Registry.ITEM.getId(getOutput().getItem()).getPath();
    }

    default List<ItemStack> getOutputs() {
        return Collections.singletonList(getOutput());
    }
}
