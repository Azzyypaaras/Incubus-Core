package net.id.incubus_core.mixin.recipe;

import com.google.gson.JsonObject;
import net.id.incubus_core.recipe.ConditionalInjections;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Enables conditional recipes
 */
@Mixin(value = RecipeManager.class, priority = Integer.MAX_VALUE)
public class RecipeManagerMixin {

    @Inject(method = "deserialize", at = @At("HEAD"), cancellable = true)
    private static void deserialize(Identifier id, JsonObject json, CallbackInfoReturnable<Recipe<?>> cir) {
        String typeId = JsonHelper.getString(json, "type");
        var typeOptional = Registry.RECIPE_TYPE.getOrEmpty(Identifier.tryParse(typeId));

        //f(typeOptional.isPresent() && ((ConditionalInjections) typeOptional.get()).supportsConditionals()) {

        //
    }
}