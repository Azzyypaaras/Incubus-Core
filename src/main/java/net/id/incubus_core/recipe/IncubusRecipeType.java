package net.id.incubus_core.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public class IncubusRecipeType<T extends Recipe<?>> implements RecipeType<T> {

    private final Identifier id;

    public IncubusRecipeType(Identifier id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public Identifier getId() {
        return id;
    }
}
