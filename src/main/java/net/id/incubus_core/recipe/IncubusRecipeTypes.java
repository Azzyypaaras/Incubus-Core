package net.id.incubus_core.recipe;

import net.minecraft.util.registry.Registry;

import static net.id.incubus_core.IncubusCore.locate;

public class IncubusRecipeTypes {

    public static void init() {
        Registry.register(Registry.RECIPE_SERIALIZER, locate("item_damaging"), ItemDamagingRecipe.Serializer.INSTANCE);
    }
}
