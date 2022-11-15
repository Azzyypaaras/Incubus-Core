package net.id.incubus_core.woodtypefactory.api.chest;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.id.incubus_core.mixin.woodtypefactory.chest.ChestBlockEntityAccessor;
import net.id.incubus_core.woodtypefactory.access.IncubusChestBlock;
import net.id.incubus_core.woodtypefactory.api.chest.client.IncubusChestBlockEntityRenderer;
import net.id.incubus_core.woodtypefactory.api.chest.client.IncubusChestTexture;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import static net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create;

/**
 * Give me the chest deets, and I'll take care of the rest.
 */
public class ChestFactory {

    private BlockEntityType<ChestBlockEntity> blockEntityType;
    public final ChestBlock chest;
    public final String modId;
    public final String chestName;

    public ChestFactory(String modId, String chestName, AbstractBlock.Settings chestBlockSettings) {
        this.modId = modId;
        this.chestName = chestName;
        this.chest = new IncubusChestBlock(chestBlockSettings, () -> this.blockEntityType);
        this.blockEntityType = create((pos, state)-> ChestBlockEntityAccessor.init(this.blockEntityType, pos, state), chest).build();

        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(modId, chestName), this.blockEntityType);
    }

    @Environment(EnvType.CLIENT)
    public void registerChestRenderers() {
        ClientChestFactory.registerChestRenderers(modId, chestName, chest, blockEntityType);
    }

    // This solution works. It's a bit weird, maybe, but it works.
    @Environment(EnvType.CLIENT)
    private static class ClientChestFactory {
        public static void registerChestRenderers(String modId, String chestName, ChestBlock chest, BlockEntityType<ChestBlockEntity> blockEntityType) {
            BuiltinItemRendererRegistry.INSTANCE.register(chest, (stack, mode, matrices, vertexConsumers, light, overlay)->{
                var dispatcher = MinecraftClient.getInstance().getBlockEntityRenderDispatcher();
                dispatcher.renderEntity(ChestBlockEntityAccessor.init(blockEntityType, BlockPos.ORIGIN, chest.getDefaultState()), matrices, vertexConsumers, light, overlay);
            });

            IncubusChestTexture texture = new IncubusChestTexture(modId, chestName);
            BlockEntityRendererRegistry.register(blockEntityType, ctx -> new IncubusChestBlockEntityRenderer(ctx, texture));

            ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
                texture.textures().forEach(spriteIdentifier -> registry.register(spriteIdentifier.getTextureId()));
            });
        }
    }

}
