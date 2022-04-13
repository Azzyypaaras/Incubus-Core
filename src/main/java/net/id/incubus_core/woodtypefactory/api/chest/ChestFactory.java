package net.id.incubus_core.woodtypefactory.api.chest;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.id.incubus_core.mixin.woodtypefactory.chest.ChestBlockEntityAccessor;
import net.id.incubus_core.woodtypefactory.access.IncubusChestBlock;
import net.id.incubus_core.woodtypefactory.api.chest.client.IncubusChestBlockEntityRenderer;
import net.id.incubus_core.woodtypefactory.api.chest.client.IncubusChestTexture;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import static net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.create;

/**
 * Give me the chest deets, and I'll take care of the rest.
 */
public class ChestFactory {
    @Environment(EnvType.CLIENT)
    private static final Set<IncubusChestTexture> allChestTextures = new HashSet<>();

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
        BuiltinItemRendererRegistry.INSTANCE.register(chest, (stack, mode, matrices, vertexConsumers, light, overlay)->{
            var dispatcher = MinecraftClient.getInstance().getBlockEntityRenderDispatcher();
            dispatcher.renderEntity(ChestBlockEntityAccessor.init(blockEntityType, BlockPos.ORIGIN, chest.getDefaultState()), matrices, vertexConsumers, light, overlay);
        });

        IncubusChestTexture texture = new IncubusChestTexture(this.modId, this.chestName);
        BlockEntityRendererRegistry.register(blockEntityType, ctx -> new IncubusChestBlockEntityRenderer(ctx, texture));

        allChestTextures.add(texture);
    }

    /**
     * Not for public use.
     */
    public static void addDefaultTextures(Consumer<SpriteIdentifier> adder){
        for(var texture : allChestTextures){
            texture.textures().forEach(adder);
        }
    }
}
