package net.id.incubus_core.item_predicates;

import net.id.incubus_core.IncubusCore;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.item.Item;

public class IncubusItemPredicates {
	
	public static ModelTransformation.Mode currentItemRenderMode;
	
	/**
	 * Registers an item predicate "incubus_core:in_world", usable in item model files
	 * Resulting values are:
	 * 0.0: rendered in a GUI, ground or fixed (item frames)
	 * 1.0: everything else (in hand, equipped, ...)
	 *
	 * @param item The item to add the item predicate to
	 */
	public static void registerInWorldItemPredicate(Item item) {
		ModelPredicateProviderRegistry.register(item, IncubusCore.locate("in_world"), (itemStack, world, livingEntity, i) -> {
			return currentItemRenderMode == ModelTransformation.Mode.GUI
					|| currentItemRenderMode == ModelTransformation.Mode.GROUND
					|| currentItemRenderMode == ModelTransformation.Mode.FIXED ? 0.0F : 1.0F;
		});
	}
	
}
