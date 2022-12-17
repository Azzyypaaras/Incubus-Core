package net.id.incubus_core.recipe.matchbook;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class MatchRegistry {

    public static final Object2ObjectArrayMap<String, MatchFactory<?>> REGISTRY = new Object2ObjectArrayMap<>();

    /**
     * Registers a Match factory.
     * @param id ensure this matches the name of the factory.
     */
    public static void register(Identifier id, MatchFactory<?> factory) {
        var name = id.toString();

        if(!name.equals(factory.name)) {
            throw new IllegalStateException("id and name of " + factory.name + " do not match!");
        }

        REGISTRY.put(name, factory);
    }

    /**
     * This is not for you.
     */
    static void registerInternal(String id, MatchFactory<?> factory) {
        REGISTRY.put(id, factory);
        REGISTRY.put("incubus:" + id, factory);
    }

    public static Optional<MatchFactory<?>> getOptional(String id) {
        return Optional.ofNullable(REGISTRY.get(id));
    }

    public static <M extends Match, T extends MatchFactory<M>> Optional<T> getOptional(String id, Class<T> clazz) {
        return Optional.ofNullable(clazz.cast(REGISTRY.get(id)));
    }
}
