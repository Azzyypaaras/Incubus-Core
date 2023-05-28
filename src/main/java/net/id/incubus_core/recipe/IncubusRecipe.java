package net.id.incubus_core.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public interface IncubusRecipe<C extends Inventory> extends Recipe<C> {

    boolean isEmpty();

    @Override
    IncubusRecipeType<?> getType();

    List<IngredientStack> getInputs();

    // TODO - Should this method ignore the DynamicRegistryManager?
    @Override
    default String getGroup() {
        return Registries.ITEM.getId(getOutput(null).getItem()).getPath();
    }

    default List<ItemStack> getOutputs(DynamicRegistryManager dynamicRegistryManager) {
        return Collections.singletonList(getOutput(dynamicRegistryManager));
    }
}
