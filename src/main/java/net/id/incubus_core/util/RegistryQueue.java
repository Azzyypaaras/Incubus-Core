package net.id.incubus_core.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * The purpose of this class is to allow you to register
 * objects while performing actions on them at the same time.
 * It also allows you to register said objects in one fell swoop
 * (All at the same time).
 * <br>Example usage:
 * <pre>{@code
 * private static RegistryQueue ITEM = new RegistryQueue(Registry.ITEM);
 *
 * private static Action<Item> compostable = (id, item) -> CompostingChanceRegistry.INSTANCE.add(item, 30);
 *
 * public static Item CANDY_CANE = ITEM.add("candy_cane", new Item(...), compostable);
 * public static Item GINGERBREAD_MAN = ITEM.add("ginger_bread_man", new Item(...), compostable);
 * }</pre>
 * More complex example usage:
 * <pre>{@code
 * private static Action<Item> fuel(int ticks) {
 *     return (id, item) -> FuelRegistry.INSTANCE.add(item, ticks);
 * }
 *
 * public static Item GIANT_STICK = ITEM.add("giant_stick", new Item(...), fuel(1000));
 * public static Item STRAW_BOWL = ITEM.add("straw_bowl", new Item(...), fuel(500));
 * }</pre>
 * Example of registration:
 * <pre>{@code
 * public static void init() {
 *     ITEM.register();
 * }
 * }</pre>
 * ~ Jack
 * @param <T> What type this RegistryQueue registers
 */
@SuppressWarnings("unused")
public final class RegistryQueue<T> {
    private static final boolean CLIENT = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    private final Registry<T> registry;
    private final Map<Identifier, Entry<? extends T>> entries;

    /**
     * Creates a {@link RegistryQueue}. This constructor is recommended for mods
     * that might add blocks regularly and/or don't want to bother dealing with
     * the other constructor.
     */
    public RegistryQueue(Registry<T> registry) {
        this.registry = registry;
        this.entries = new LinkedHashMap<>(16);
    }

    /**
     * Creates a {@link RegistryQueue} with an initial capacity for minor speed and memory improvements,
     * if you're into that sort of thing. Make sure to be diligent when choosing the initial capacity.
     * @param initialCapacity The initial capacity of this {@link RegistryQueue}'s map. This should
     *                        ideally be equal to the amount of things you intend to register.
     */
    public RegistryQueue(Registry<T> registry, int initialCapacity) {
        this.registry = registry;
        /*
        A load factor of 0.10F is chosen because people will forget
        to update this value when they add a few new blocks.
        The default load factor is 0.75F, which is ridiculous for this use-case.
         */
        this.entries = new LinkedHashMap<>(initialCapacity, 0.10F);
    }

    /**
     * Makes the action only act when the {@link EnvType} is {@link EnvType#CLIENT}.
     * @return This action, modified to only occur in a client environment.
     */
    public static <S> Action<S> onClient(Action<S> action) {
        return CLIENT ? action : (id, value) -> {};
    }

    /**
     * Adds an entry to the {@link RegistryQueue}. If the id is a duplicate, the entry in this RegistryQueue is replaced.
     * @param id The identifier of the object
     * @param value The object to be associated with the identifier
     * @param additionalActions Any additional actions to perform on the object
     * @param <V> The type or a subtype of this {@link RegistryQueue}'s type T.
     * @return The {@code value} inputted into this method
     */
    @SafeVarargs
    public final <V extends T> V add(Identifier id, V value, BiConsumer<Identifier, ? super V>... additionalActions) {
        this.entries.put(id, new Entry<>(value, additionalActions));
        return value;
    }

    /**
     * Registers all of the entries in this {@link RegistryQueue} and clears
     * this RegistryQueue's data from memory. All actions assigned to objects
     * in this RegistryQueue will be performed at this time.
     */
    public void register() {
        this.entries.forEach(this::register);
        this.entries.clear();
    }

    private <V extends T> void register(Identifier id, Entry<V> pair) {
        Registry.register(this.registry, id, pair.value);
        for (var action : pair.actions) {
            action.accept(id, pair.value);
        }
    }

    /**
     * An interface to represent an action to be performed on an object.
     * Made for ease of use.
     * @param <T> The type of the object this Action acts upon.
     */
    @FunctionalInterface
    public interface Action<T> extends BiConsumer<Identifier, T> {
    }

    /**
     * A record to represent an object and its actions to be performed on it.
     * @param <V> The type of object
     */
    private static record Entry<V>(V value, BiConsumer<Identifier, ? super V>[] actions) {
    }
}
