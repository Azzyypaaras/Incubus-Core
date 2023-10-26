package net.id.incubus_core.recipe;

import net.minecraft.inventory.*;
import net.minecraft.recipe.*;

import java.util.*;

@SuppressWarnings("unused")
public interface IncubusRecipe<C extends Inventory> extends Recipe<C> {

    boolean isEmpty();

    @Override
    IncubusRecipeType<?> getType();

    List<IngredientStack> getInputs();
    
}
