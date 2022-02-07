package net.id.incubus_core.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.tick.OrderedTick;

public class UnderwaterStateFeature extends Feature<SingleStateFeatureConfig> {

    public UnderwaterStateFeature(Codec<SingleStateFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<SingleStateFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        SingleStateFeatureConfig config = context.getConfig();
        BlockPos pos = context.getOrigin();

        if(!world.isWater(pos)) {
            return false;
        }

        if(!world.isWater(pos.down())) {
            world.setBlockState(pos.down(), config.state, 2);
            world.getBlockTickScheduler().scheduleTick(OrderedTick.create(config.state.getBlock(), pos));
            return true;
        }

        return false;
    }
}
