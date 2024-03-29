package net.id.incubus_core.devel;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.id.incubus_core.util.Config;
import net.minecraft.util.Identifier;

import java.nio.file.Path;

import static net.id.incubus_core.IncubusCore.locate;
import static net.id.incubus_core.devel.Devel.BAD_FEATURES;
import static net.id.incubus_core.devel.Devel.ClientDevel;
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
    public static final class DevelConfig {
        public static final boolean PRINT_SETBLOCK_STACK_TRACE = Config.getBoolean(locate("devel.setblock_stack_trace"), false);
        public static final Path DIRECTORY = getPath(locate("devel.directory"), Path.of("./devel"));
        public static final String[] MODS = getStringArray(locate("devel.mods"), new String[0]);

        private static String[] getStringArray(Identifier key, String[] defaultValue) {
            return Config.get(key, (s) -> s.split(","), defaultValue);
        }

        private static Path getPath(Identifier key, Path defaultValue) {
            return Config.get(key, Path::of, defaultValue);
        }
    }
}
