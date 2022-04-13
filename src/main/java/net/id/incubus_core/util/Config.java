package net.id.incubus_core.util;

import net.fabricmc.loader.api.FabricLoader;
import net.id.incubus_core.IncubusCore;
import net.minecraft.util.Identifier;

import java.util.function.Function;

/**
 * Call my methods in order to use these useful config features.
 */
@SuppressWarnings("unused")
public final class Config {
    private Config(){}

    /**
     * @return Whether the mod with the specified id is loaded.
     */
    public static boolean isLoaded(String id){
        return FabricLoader.getInstance().isModLoaded(id);
    }

    /**
     * Gets a boolean system property.
     */
    public static boolean getBoolean(Identifier key, boolean defaultValue){
        return get(key, Boolean::parseBoolean, defaultValue);
    }

    /**
     * Gets a string system property.
     */
    public static String getString(Identifier key, String defaultValue){
        return get(key, String::toString, defaultValue);
    }

    /**
     * Gets an identifier system property.
     */
    public static Identifier getIdentifier(Identifier key, Identifier defaultValue) {
        return get(key, Identifier::tryParse, defaultValue);
    }

    /**
     * Gets a system property, parsed by the specified function.
     */
    public static <T> T get(Identifier key, Function<String, T> parser, T defaultValue){
        String value = System.getProperty(key.getNamespace() + "." + key.getPath());
        if(value != null && !value.isBlank()){
            try{
                return parser.apply(value);
            } catch (Throwable t) {
                IncubusCore.LOG.error("Failed to parse {}.{}: {}. using default value {}.", key.getNamespace(), key.getPath(), value, defaultValue);
                t.printStackTrace();
            }
        }
        return defaultValue;
    }
}
