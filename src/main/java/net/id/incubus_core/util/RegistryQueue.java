package net.id.incubus_core.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.id.incubus_core.IncubusCore;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@SuppressWarnings("unused")
public final class RegistryQueue<T> {
    private static final boolean CLIENT = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    private final Registry<T> registry;
    private final List<Entry<? extends T>> entries;
    private final String namespace;

    public RegistryQueue(Registry<T> registry, String modID, int initialCapacity) {
        this.registry = registry;
        this.namespace = modID;
        entries = new ArrayList<>(initialCapacity);
    }

    public RegistryQueue(Registry<T> registry, int initialCapacity) {
        this(registry, null, initialCapacity);
    }

    public static <S> Action<S> onClient(Action<S> action) {
        return CLIENT ? action : (id, value) -> {};
    }

    @SafeVarargs
    public final <V extends T> V add(Identifier id, V value, BiConsumer<Identifier, ? super V>... additionalActions) {
        this.entries.add(new Entry<>(id, value, additionalActions));
        return value;
    }

    @SafeVarargs
    public final <V extends T> V add(String id, V value, BiConsumer<Identifier, ? super V>... additionalActions) {
        if (id.contains(":")) {
            return this.add(new Identifier(id), value, additionalActions);
        }
        if (namespace == null) {
            throw new NullPointerException("Identifier " + id + " registered to " + value.getClass().getName() + " registry queue without a namespace.");
        }
        return this.add(new Identifier(namespace, id), value, additionalActions);
    }

    public void register() {
        for (Entry<? extends T> entry : entries) {
            register(entry);
        }
        entries.clear();
    }

    private <V extends T> void register(Entry<V> entry) {
        Registry.register(this.registry, entry.id, entry.value);
        for (BiConsumer<Identifier, ? super V> action : entry.additionalActions) {
            action.accept(entry.id, entry.value);
        }
    }

    public interface Action<T> extends BiConsumer<Identifier, T> {
    }

    private static record Entry<V>(Identifier id, V value, BiConsumer<Identifier, ? super V>[] additionalActions) {
    }
}
