package net.id.incubus_core.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.id.incubus_core.item_predicates.IncubusItemPredicates;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
	
	@Inject(at = @At("HEAD"), method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V")
	private void incubus_core$storeItemRenderMode1(LivingEntity entity, ItemStack item, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, World world, int light, int overlay, int seed, CallbackInfo ci) {
		IncubusItemPredicates.currentItemRenderMode = renderMode;
	}
	
	@Inject(at = @At("HEAD"), method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V")
	private void incubus_core$storeItemRenderMode2(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci) {
		IncubusItemPredicates.currentItemRenderMode = renderMode;
	}
	
	@Inject(at = @At("HEAD"), method = "innerRenderInGui(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;IIII)V")
	private void incubus_core$storeItemRenderMode3(LivingEntity entity, ItemStack itemStack, int x, int y, int seed, int depth, CallbackInfo ci) {
		IncubusItemPredicates.currentItemRenderMode = ModelTransformation.Mode.GUI;
	}
	
}
