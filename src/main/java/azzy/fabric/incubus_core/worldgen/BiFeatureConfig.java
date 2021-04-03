package azzy.fabric.incubus_core.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.FeatureConfig;

public class BiFeatureConfig implements FeatureConfig {
    @SuppressWarnings("CodeBlock2Expr")
    public static final Codec<BiFeatureConfig> CODEC = RecordCodecBuilder.create(biFeatureConfigInstance -> {
        return biFeatureConfigInstance.group(BlockState.CODEC.fieldOf("primState").forGetter((diskFeatureConfig) -> {
            return diskFeatureConfig.primaryState;
        }), BlockState.CODEC.fieldOf("secState").forGetter((diskFeatureConfig) -> {
            return diskFeatureConfig.secondaryState;
        }), Codec.floatRange(0, 1).fieldOf("chance").forGetter((diskFeatureConfig) -> {
            return diskFeatureConfig.chance;
        }), UniformIntDistribution.CODEC.fieldOf("scale").forGetter((diskFeatureConfig) -> {
            return diskFeatureConfig.scale;
        })).apply(biFeatureConfigInstance, BiFeatureConfig::new);
    });

    public final BlockState primaryState;
    public final BlockState secondaryState;
    public final Float chance;
    public final UniformIntDistribution scale;

    public BiFeatureConfig(BlockState primaryState, BlockState secondaryState, float chance, UniformIntDistribution scale) {
        this.primaryState = primaryState;
        this.secondaryState = secondaryState;
        this.chance = chance;
        this.scale = scale;
    }
}
