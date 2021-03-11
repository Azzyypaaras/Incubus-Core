package azzy.fabric.incubus_core.mixin;

import azzy.fabric.incubus_core.recipe.MatchingStackAccessor;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Ingredient.class)
public abstract class IngredientMixin implements MatchingStackAccessor {

    @Shadow protected abstract void cacheMatchingStacks();

    @Shadow private ItemStack[] matchingStacks;

    @Override
    public ItemStack[] getMatchingStacks() {
        cacheMatchingStacks();
        return matchingStacks;
    }
}
