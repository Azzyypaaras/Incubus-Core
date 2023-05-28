package net.id.incubus_core.systems;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.id.incubus_core.util.RandomShim;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Collections;

public class HeatHelper {

    public static double exchangeHeat(Material medium, double tempA, double tempB, double exchangeArea) {
        return medium.heatConductivity() * exchangeArea * (tempA - tempB);
    }

    public static double playerAmbientHeat(Material medium, double playerTemp, BlockPos playerPos, RegistryEntry<Biome> entry, boolean night, boolean rain) {
        return -exchangeHeat(medium, playerTemp, translateBiomeHeat(playerPos, entry, night, rain), 4.5);
    }

    public static double simulateAmbientHeating(HeatIo io, World world, BlockPos pos, Simulation simulation) {
        var validExchangeDirs = io.getValidDirections();

        if (!validExchangeDirs.isEmpty()) {
            Collections.shuffle(validExchangeDirs, new RandomShim(world.getRandom()));
            var exchangeDir = io.getPreferredDirection().orElse(validExchangeDirs.get(0));

            if (validExchangeDirs.contains(exchangeDir)) {
                double transfer = exchangeHeat(DefaultMaterials.AIR, io.getTemperature(), translateBiomeHeat(pos, world.getBiome(pos), world.isNight(), world.isRaining()), io.getExchangeArea(exchangeDir));
                if (simulation == Simulation.ACT) {
                    io.cool(transfer);
                }
                return transfer;
            }
        }
        return 0;
    }

    public static double translateBiomeHeat(BlockPos pos, RegistryEntry<Biome> biomeEntry, boolean night, boolean rain) {

        Biome biome = biomeEntry.value();
        double temp = biome.getTemperature();

        temp *= 26.25;

        if (night) {
            if (biomeEntry.isIn(ConventionalBiomeTags.DESERT) || biomeEntry.isIn(ConventionalBiomeTags.MESA)) {
                temp -= temp * 0.75;
            } else if (biome.getPrecipitation(pos) == Biome.Precipitation.SNOW) {
                if (biomeEntry.isIn(ConventionalBiomeTags.ICY) && temp < 0)
                    temp += temp * 1.25;
                else
                    temp -= 20;

            } else {
                temp -= (biome.getPrecipitation(pos) == Biome.Precipitation.NONE) ? 12 : 4;
            }
        }
        if (rain) {
            temp -= biome.getPrecipitation(pos) == Biome.Precipitation.SNOW || biome.getPrecipitation(pos) == Biome.Precipitation.RAIN && pos.getY() >= 100 ? 10 : 3;
        }
        return temp;
    }
}
