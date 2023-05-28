package net.id.incubus_core.recipe;

import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class ItemDamagingRecipe extends ShapelessRecipe {

    public ItemDamagingRecipe(ShapelessRecipe parent) {
        super(parent.getId(), parent.getGroup(), parent.getCategory(), parent.getOutput(null), parent.getIngredients());
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingInventory inventory) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);
        for (int i = 0; i < defaultedList.size(); ++i) {
            ItemStack item = inventory.getStack(i);
            if (item.getItem().isDamageable() && item.getDamage() + 1 < item.getMaxDamage()) { // Override damageable, fallback onto remainders
                item = item.copy();
                item.setDamage(item.getDamage() + 1); // Damage item by one
                defaultedList.set(i, item);
            } else if (item.getItem().hasRecipeRemainder()) {
                defaultedList.set(i, new ItemStack(item.getItem().getRecipeRemainder()));
            }
        }
        return defaultedList;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return net.id.incubus_core.recipe.ItemDamagingRecipe.Serializer.INSTANCE;
    }

    public static class Serializer extends ShapelessRecipe.Serializer {

        public static final ItemDamagingRecipe.Serializer INSTANCE = new net.id.incubus_core.recipe.ItemDamagingRecipe.Serializer();

        @Override
        public ShapelessRecipe read(Identifier identifier, JsonObject jsonObject) {
            return new ItemDamagingRecipe(super.read(identifier, jsonObject));
        }

        @Override
        public ShapelessRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            return new ItemDamagingRecipe(super.read(identifier, packetByteBuf));
        }
    }
}

