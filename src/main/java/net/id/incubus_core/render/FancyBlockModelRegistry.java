package net.id.incubus_core.render;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.renderer.v1.material.BlendMode;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A simple way to create simple blocks with fancy rendering.
 *
 * Currently supported features:
 *  - Albedo maps
 *  - Emissive maps
 *
 * @since 1.7
 *
 * @author gudenau
 */
@Environment(EnvType.CLIENT)
public final class FancyBlockModelRegistry {
    private FancyBlockModelRegistry() {}

    private static final Map<Identifier, FancyBlockModel> BLOCK_MODELS = new HashMap<>();

    /**
     * @hidden
     */
    public static void init() {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider((resourceManager)->(resourceId, context)->BLOCK_MODELS.get(resourceId));
    }

    /**
     * Creates a new model builder.
     *
     * @return The new model builder
     */
    @Contract(value = "-> new", pure = true)
    public static Builder builder() {
        return new Builder();
    }

    /**
     * The builder type for fancy blocks.
     */
    public static final class Builder {
        private SpriteIdentifier particle;
        private final Map<Texture, Pair<SpriteIdentifier, BlendMode>> textures = new HashMap<>();

        private Builder(){}

        /**
         * Sets the particle sprite for the current model.
         *
         * This has the same behaviour as
         * {@snippet :
         * builder.particle(new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, particle));
         * }
         *
         * @param particle the identifier of the particle
         * @return this
         */
        @Contract("_ -> this")
        public Builder particle(@NotNull Identifier particle) {
            Objects.requireNonNull(particle, "particle can't be null");
            this.particle = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, particle);
            return this;
        }

        /**
         * Sets the particle sprite for the current model.
         *
         * @param particle the identifier of the particle
         * @return this
         */
        @Contract("_ -> this")
        public Builder particle(@NotNull SpriteIdentifier particle) {
            Objects.requireNonNull(particle, "particle can't be null");
            this.particle = particle;
            return this;
        }

        /**
         * Adds a texture to the current builder.
         *
         * This has the same behaviour as
         * {@snippet :
         * builder.texture(type, new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, identifier), BlendMode.SOLID);
         * }
         * for albedo maps and
         * {@snippet :
         * builder.texture(type, new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, identifier), BlendMode.CUTOUT_MIPPED);
         * }
         * for emissive maps.
         *
         * @param type The type of the texture
         * @param identifier The identifier of the texture
         * @return this
         */
        @Contract("_, _ -> this")
        public Builder texture(@NotNull Texture type, @NotNull Identifier identifier) {
            Objects.requireNonNull(type, "type can't be null");
            Objects.requireNonNull(identifier, "identifier can't be null");
            if(type == Texture.PARTICLE) {
                throw new IllegalArgumentException("type can't be PARTICLE, use particle(Identifier) instead!");
            }

            if(textures.putIfAbsent(type, new Pair<>(
                    new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, identifier),
                    type == Texture.EMISSIVE ? BlendMode.CUTOUT_MIPPED : BlendMode.SOLID
            )) != null) {
                throw new IllegalStateException("Texture " + type.name() + " was already provided.");
            }

            return this;
        }

        /**
         * Adds a texture to the current builder.
         *
         * This has the same behaviour as
         * {@snippet :
         * builder.texture(type, identifier, BlendMode.SOLID);
         * }
         * for albedo maps and
         * {@snippet :
         * builder.texture(type, identifier, BlendMode.CUTOUT_MIPPED);
         * }
         * for emissive maps.
         *
         * @param type The type of the texture
         * @param identifier The identifier of the texture
         * @return this
         */
        @Contract("_, _ -> this")
        public Builder texture(@NotNull Texture type, @NotNull SpriteIdentifier identifier) {
            Objects.requireNonNull(type, "type can't be null");
            Objects.requireNonNull(identifier, "identifier can't be null");
            if(type == Texture.PARTICLE) {
                throw new IllegalArgumentException("type can't be PARTICLE, use particle(SpriteIdentifier) instead!");
            }

            if(textures.putIfAbsent(type, new Pair<>(
                    identifier,
                    type == Texture.EMISSIVE ? BlendMode.CUTOUT_MIPPED : BlendMode.SOLID
            )) != null) {
                throw new IllegalStateException("Texture " + type.name() + " was already provided.");
            }

            return this;
        }

        /**
         * Adds a texture to the current builder.
         *
         * This has the same behaviour as
         * {@snippet :
         * builder.texture(type, new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, identifier), mode);
         * }
         *
         * @param type The type of the texture
         * @param identifier The identifier of the texture
         * @param mode The render mode of the texture
         * @return this
         */
        @Contract("_, _, _ -> this")
        public Builder texture(@NotNull Texture type, @NotNull Identifier identifier, @NotNull BlendMode mode) {
            Objects.requireNonNull(type, "type can't be null");
            Objects.requireNonNull(identifier, "identifier can't be null");
            Objects.requireNonNull(mode, "mode can't be null");
            if(type == Texture.PARTICLE) {
                throw new IllegalArgumentException("type can't be PARTICLE, use particle(Identifier) instead!");
            }

            if(textures.putIfAbsent(type, new Pair<>(
                    new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, identifier),
                    mode
            )) != null) {
                throw new IllegalStateException("Texture " + type.name() + " was already provided.");
            }

            return this;
        }

        /**
         * Adds a texture to the current builder.
         *
         * @param type The type of the texture
         * @param identifier The identifier of the texture
         * @param mode The render mode of the texture
         * @return this
         */
        @Contract("_, _, _ -> this")
        public Builder texture(@NotNull Texture type, @NotNull SpriteIdentifier identifier, @NotNull BlendMode mode) {
            Objects.requireNonNull(type, "type can't be null");
            Objects.requireNonNull(identifier, "identifier can't be null");
            Objects.requireNonNull(mode, "mode can't be null");
            if(type == Texture.PARTICLE) {
                throw new IllegalArgumentException("type can't be PARTICLE, use particle(SpriteIdentifier) instead!");
            }

            if(textures.putIfAbsent(type, new Pair<>(identifier, mode)) != null) {
                throw new IllegalStateException("Texture " + type.name() + " was already provided.");
            }

            return this;
        }

        /**
         * Builds and registers the fancy model that this builder currently represents.
         *
         * @param identifier The identifier for the new model
         */
        public void register(@NotNull Identifier identifier) {
            if(textures.isEmpty()) {
                throw new IllegalStateException("No textures where defined!");
            }
            if(particle == null) {
                throw new IllegalStateException("Particle texture was not defined!");
            }

            if(BLOCK_MODELS.putIfAbsent(identifier, new FancyBlockModel(Stream.concat(
                    Stream.of(new TextureEntry(Texture.PARTICLE, particle, BlendMode.SOLID)),
                    textures.entrySet().stream()
                            .map((entry)->{
                                var value = entry.getValue();
                                return new TextureEntry(entry.getKey(), value.getFirst(), value.getSecond());
                            })
            ).collect(Collectors.toSet())
            )) != null){
                throw new IllegalStateException("Model " + identifier + " was already registered");
            }

            particle = null;
            textures.clear();
        }
    }

    record TextureEntry(
            @NotNull Texture type,
            @NotNull SpriteIdentifier identifier,
            @NotNull BlendMode blendMode
    ) {}

    /**
     * The different types of textures that are supported by fancy block models.
     */
    public enum Texture {
        /**
         * @hidden Internal use only.
         */
        PARTICLE,
        /**
         * The "normal" texture maps that all vanilla blocks use.
         */
        ALBEDO,
        /**
         * Appears to emmit light at all times without emitting light in the engine.
         */
        EMISSIVE,
    }
}
