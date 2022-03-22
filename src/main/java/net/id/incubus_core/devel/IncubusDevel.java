package net.id.incubus_core.devel;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.id.incubus_core.IncubusCore;
import net.minecraft.util.Identifier;

import java.nio.file.Path;
import java.util.function.Function;

import static net.id.incubus_core.devel.Devel.*;
import static net.id.incubus_core.devel.Devel.ClientDevel.*;

/**
 * This is not for public use.
 */
public final class IncubusDevel {
    /**
     * This is not for public use.
     */
    public static void init() {
        if (!FabricLoader.getInstance().isDevelopmentEnvironment()) {
            throw new RuntimeException("Trying to initiate devel tools in production!");
        }
        Devel.init();
    }

    /**
     * This is especially not for public use.
     */
    @Environment(EnvType.CLIENT)
    public static void initClient() {
        if (!FabricLoader.getInstance().isDevelopmentEnvironment()) {
            throw new RuntimeException("Trying to initiate client devel tools in production!");
        }
        ClientDevel.clientInit();
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
    @SuppressWarnings("SameParameterValue")
    public static final class Config{
        public static final boolean PRINT_SETBLOCK_STACK_TRACE = getBoolean("devel.setblockStackTrace", false);
        public static final Path DIRECTORY = getPath("devel.directory", Path.of("./devel"));
        public static final String[] MODS = getStringArray("devel.mods", new String[0]);

        private static boolean getBoolean(String key, boolean defaultValue){
            return getKey(key, Boolean::parseBoolean, defaultValue);
        }

        private static String[] getStringArray(String key, String[] defaultValue) {
            return getKey(key, (s) -> s.split(","), defaultValue);
        }

        private static Path getPath(String key, Path defaultValue) {
            return getKey(key, Path::of, defaultValue);
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
