package net.id.incubus_core.devel;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.id.incubus_core.IncubusCore;
import net.minecraft.util.Identifier;

import java.util.function.Function;

import static net.id.incubus_core.devel.Devel.*;

/**
 * This is not for public use.
 */
public final class IncubusDevel {
    /**
     * This is not for public use.
     */
    // TODO MIGRATION: This should probably be moved to a mixin so it's after everything.
    public static void init() {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            if (Config.MINECRAFT_DEVEL) {
                Devel.createDevelFor("minecraft");
            }
            Devel.init();
        }
    }

    /**
     * This is especially not for public use.
     */
    @Environment(EnvType.CLIENT)
    public static void initClient() {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            Devel.clientInit();
        }
    }

    /**
     * This is not for public use.
     */
    public static void logBadFeature(String feature){
        synchronized(BAD_FEATURES){
            BAD_FEATURES.add(feature);
        }
    }

    /**
     * This is especially not for public use.
     */
    @Environment(EnvType.CLIENT)
    public static void logMissingTexture(Identifier identifier){
        synchronized(MISSING_TEXTURES){
            MISSING_TEXTURES.add(identifier);
        }
    }

    /**
     * This is especially not for public use.
     */
    @Environment(EnvType.CLIENT)
    public static void logBadTexture(Identifier identifier){
        synchronized(BAD_TEXTURES){
            BAD_TEXTURES.add(identifier);
        }
    }

    /**
     * This is especially not for public use.
     */
    @Environment(EnvType.CLIENT)
    public static void logMissingLanguageKey(String key){
        synchronized(MISSING_LANGUAGE_KEYS){
            MISSING_LANGUAGE_KEYS.add(key);
        }
    }

    /**
     * Configuration options for development. Mostly internal stuff you don't need to bother with.
     */
    public static final class Config{
        public static final boolean PRINT_SETBLOCK_STACK_TRACE = getBoolean("setblockStackTrace", false);
        public static final boolean MINECRAFT_DEVEL = getBoolean("minecraftDevel", false);
        public static final String DIRECTORY = getKey("directory", String::toString, "./devel");

        private static boolean getBoolean(String key, boolean defaultValue){
            return getKey(key, Boolean::parseBoolean, defaultValue);
        }

        private static <T> T getKey(String key, Function<String, T> parser, T defaultValue){
            String value = System.getProperty(IncubusCore.MODID + '.' + key);
            if(value != null && !value.isBlank()){
                try{
                    return parser.apply(value);
                }catch(Throwable t){
                    IncubusCore.LOG.error("Failed to parse {}.{}: {}", IncubusCore.MODID, key, value);
                    t.printStackTrace();
                }
            }
            return defaultValue;
        }
    }
}
