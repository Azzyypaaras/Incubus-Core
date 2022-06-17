package net.id.incubus_core.render;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Environment(EnvType.CLIENT)
final class FancyBlockModel extends JsonUnbakedModel {
    private static final Identifier DEFAULT_BLOCK_MODEL = new Identifier(Identifier.DEFAULT_NAMESPACE, "block/block");
    
    private final Set<FancyBlockModelRegistry.TextureEntry> textures;
    
    FancyBlockModel(Set<FancyBlockModelRegistry.TextureEntry> textures) {
        super(DEFAULT_BLOCK_MODEL, List.of(), Map.of(), true, null, ModelTransformation.NONE, List.of());
        
        this.textures = Set.copyOf(textures);
    }
    
    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }
    
    @Override
    public Collection<Identifier> getModelDependencies() {
        return Set.of(DEFAULT_BLOCK_MODEL);
    }
    
    @Override
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
        return textures.stream()
            .map(FancyBlockModelRegistry.TextureEntry::identifier)
            .collect(Collectors.toUnmodifiableSet());
    }
    
    @Override
    public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        if(!(loader.getOrLoadModel(DEFAULT_BLOCK_MODEL) instanceof JsonUnbakedModel defaultModel)){
            throw new AssertionError("The default block model (minecraft:block/block) was not a JsonUnbakedModel!");
        }
        var transformation = defaultModel.getTransformations();
        
        var renderer = RendererAccess.INSTANCE.getRenderer();
        if(renderer == null) {
            throw new IllegalStateException("RenderAccess.getRenderer() returned null!");
        }
        
        var finder = renderer.materialFinder();
        var builder = renderer.meshBuilder();
        var emitter = builder.getEmitter();
        
        Sprite particle = null;
        
        for(var texture : textures) {
            finder.clear();
    
            var sprite = textureGetter.apply(texture.identifier());
    
            switch (texture.type()) {
                case PARTICLE -> {
                    particle = sprite;
                    continue;
                }
                
                case EMISSIVE -> finder.emissive(0, true);
            }
            
            var material = finder.blendMode(0, texture.blendMode())
                .find();
            
            for (var face : Direction.values()) {
                emitter.material(material);
                emitter.square(face, 0, 0, 1, 1, 0);
                emitter.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV);
                emitter.spriteColor(0, -1, -1, -1, -1);
                emitter.emit();
            }
        }
        
        if(particle == null) {
            throw new IllegalStateException("No particle for fancy block!");
        }
        
        return new Baked(
            builder.build(),
            particle,
            transformation
        );
    }
    
    private record Baked(
        @NotNull Mesh mesh,
        @NotNull Sprite particle,
        @NotNull ModelTransformation transformation
    ) implements BakedModel, FabricBakedModel {
        @Override
        public boolean isVanillaAdapter() {
            return false;
        }
    
        @Override
        public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<net.minecraft.util.math.random.Random> randomSupplier, RenderContext context) {
            context.meshConsumer().accept(mesh);
        }
    
        @Override
        public void emitItemQuads(ItemStack stack, Supplier<net.minecraft.util.math.random.Random> randomSupplier, RenderContext context) {
            context.meshConsumer().accept(mesh);
        }
        
        @Override
        public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, net.minecraft.util.math.random.Random random) {
            return null;
        }
    
        @Override
        public boolean useAmbientOcclusion() {
            return true;
        }
    
        @Override
        public boolean hasDepth() {
            return false;
        }
    
        @Override
        public boolean isSideLit() {
            return true;
        }
    
        @Override
        public boolean isBuiltin() {
            return false;
        }
    
        @Override
        public Sprite getParticleSprite() {
            return particle;
        }
    
        @Override
        public ModelTransformation getTransformation() {
            return transformation;
        }
    
        @Override
        public ModelOverrideList getOverrides() {
            return ModelOverrideList.EMPTY;
        }
    }
}
