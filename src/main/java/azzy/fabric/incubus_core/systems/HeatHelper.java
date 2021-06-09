package azzy.fabric.incubus_core.systems;

import net.minecraft.world.biome.Biome;

public class HeatHelper {

    public static double exchangeHeat(HeatMaterial medium, double tempA, double tempB, double exchangeArea) {
        return medium.transfer * exchangeArea * (tempA - tempB);
    }

    public static double playerAmbientHeat(HeatMaterial medium, double playerTemp, Biome biome, boolean night, boolean rain, int height) {
        return -exchangeHeat(medium, playerTemp, translateBiomeHeat(biome, night, rain, height), 4.5);
    }

    public static double translateBiomeHeat(Biome biome, boolean night, boolean rain, int height) {
        double temp;
        if (biome.getCategory() == Biome.Category.NETHER) {
            temp = (biome.getTemperature() + 2) * 31.25;
        } else if (biome.getCategory() == Biome.Category.THEEND) {
            temp = (biome.getTemperature() - 1.5) * 31.25;
        } else {
            temp = biome.getTemperature() * 31.25;
        }
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
            temp -= biome.getPrecipitation() == Biome.Precipitation.SNOW || biome.getPrecipitation() == Biome.Precipitation.RAIN && height >= 100 ? 10 : 3;
        }
        return temp;
    }

    public enum HeatMaterial {
        NULL(0.0),
        AIR(0.026),
        BRICK(0.6),
        STEEL(50.2),
        WATER(0.6),
        GENERIC_FLUID(0.35),
        DIAMOND(335.2),
        GRANITE(1.73),
        BEDROCK(400);

        private final double transfer;

        HeatMaterial(double transfer) {
            this.transfer = transfer / 419.0;
        }

        public static HeatMaterial nullableValueOf(String name){
            try {
                return HeatMaterial.valueOf(name);
            } catch (Exception ignored){
                return NULL;
            }
        }
    }
}
