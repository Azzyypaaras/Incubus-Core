package net.id.incubus_core.systems;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Collections;

public class HeatHelper {

    public static double exchangeHeat(Material medium, double tempA, double tempB, double exchangeArea) {
        return medium.heatConductivity() * exchangeArea * (tempA - tempB);
    }

    public static double playerAmbientHeat(Material medium, double playerTemp, BlockPos playerPos, Biome biome, boolean night, boolean rain) {
        return -exchangeHeat(medium, playerTemp, translateBiomeHeat(playerPos, biome, night, rain), 4.5);
    }

    public static double simulateAmbientHeating(HeatIo io, World world, BlockPos pos, Simulation simulation) {
        var validExchangeDirs = io.getValidDirections();

        if(!validExchangeDirs.isEmpty()) {
            Collections.shuffle(validExchangeDirs, world.getRandom());
            var exchangeDir = io.getPreferredDirection().orElse(validExchangeDirs.get(0));

            if(validExchangeDirs.contains(exchangeDir)) {
                double transfer = exchangeHeat(DefaultMaterials.AIR, io.getTemperature(), translateBiomeHeat(pos, world.getBiome(pos), world.isNight(), world.isRaining()), io.getExchangeArea(exchangeDir));
                if(simulation == Simulation.ACT) {
                    io.cool(transfer);
                }
                return transfer;
            }
        }
        return 0;
    }

    public static double translateBiomeHeat(BlockPos pos, Biome biome, boolean night, boolean rain) {

        double baseTemp = biome.getTemperature(pos);

        double temp = switch (biome.getCategory()) {
            case NETHER -> baseTemp + 1;
            case THEEND -> baseTemp - 1.5;
            case DESERT -> baseTemp - 0.25;
            case OCEAN -> baseTemp + 0.1;
            default -> baseTemp;
        };

        temp *= 26.25;

        if(night) {
            if(biome.getCategory() == Biome.Category.DESERT || biome.getCategory() == Biome.Category.MESA) {
                temp -= temp * 0.75;
            } else if(biome.getPrecipitation() == Biome.Precipitation.SNOW) {
                if(biome.getCategory() == Biome.Category.ICY && temp < 0)
                    temp += temp * 1.25;
                else
                    temp -= 20;

            } else {
                temp -= (biome.getPrecipitation() == Biome.Precipitation.NONE) ? 12 : 4;
            }
        }
        if(rain) {
            temp -= biome.getPrecipitation() == Biome.Precipitation.SNOW || biome.getPrecipitation() == Biome.Precipitation.RAIN && pos.getY() >= 100 ? 10 : 3;
        }
        return temp;
    }
}
