package net.id.incubus_core.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.tick.OrderedTick;

import java.util.LinkedList;
import java.util.Queue;

public class UnderwaterCrustFeature extends Feature<BiFeatureConfig> {

    Queue<BlockPos> centers;

    public UnderwaterCrustFeature(Codec<BiFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<BiFeatureConfig> context) {

        BlockPos center = context.getOrigin();
        BiFeatureConfig config = context.getConfig();
        StructureWorldAccess world = context.getWorld();
        var random = context.getRandom();

        if(!world.getFluidState(center).isIn(FluidTags.WATER))
            return false;

        center = center.down();

        int radius = config.scale.get(random);

        centers = generateCircleAndCenters(world, center, radius, random, config);

        radius = config.scale.get(random);

        Queue<BlockPos> holder = new LinkedList<>();

        for (BlockPos blockPos : centers) {
            holder.addAll(generateCircleAndCenters(world, blockPos, radius, random, config));
        }

        centers.clear();
        centers.addAll(holder);

        radius = config.scale.get(random);

        for (BlockPos newCenter : centers) {
            generateCircleAndCenters(world, newCenter, radius, random, config);
        }

        return true;
    }

    private Queue<BlockPos> generateCircleAndCenters(StructureWorldAccess world, BlockPos center, int radius, Random random, BiFeatureConfig config) {
        Queue<BlockPos> centers = new LinkedList<>();
        BlockPos.iterateOutwards(center, radius, 0, radius).forEach(pos -> {
            if(pos.getSquaredDistance(center) > Math.pow(radius, 2))
                return;

            boolean add = false;

            if(pos.getManhattanDistance(center) == radius && random.nextFloat() < 0.1F)
                add = true;

            if(world.isWater(pos) || !world.isWater(pos.up())) {
                boolean fail = true;
                int tries = 5;

                while(tries >= -4) {
                    tries--;
                    pos = pos.offset(Direction.Axis.Y, tries);
                    if(!world.isAir(pos) && !world.isWater(pos) && world.isWater(pos.up())) {
                        fail = false;
                        break;
                    }
                }

                if(fail)
                    return;
            }

            if(add)
                centers.add(pos);

            BlockState state = random.nextFloat() <= config.chance ? config.secondaryState : config.primaryState;

            world.setBlockState(pos, state, 2);
            world.getBlockTickScheduler().scheduleTick(OrderedTick.create(state.getBlock(), pos));
        });
        return centers;
    }
}
