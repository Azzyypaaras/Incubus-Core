package net.id.incubus_core.recipe;

import static net.id.incubus_core.IncubusCore.locate;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class IncubusRecipeTypes {

    public static void init() {
        Registry.register(Registries.RECIPE_SERIALIZER, locate("item_damaging"), ItemDamagingRecipe.Serializer.INSTANCE);
    }
}
