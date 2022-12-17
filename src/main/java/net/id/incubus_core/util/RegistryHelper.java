package net.id.incubus_core.util;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class RegistryHelper {

    public static <T> Optional<TagKey<T>> tryGetTagKey(Registry<T> registry, Identifier id) {
        return registry.streamTags().filter(tagKey -> tagKey.id().equals(id)).findFirst();
    }

    public static <T> Optional<? extends RegistryEntryList<T>> getEntries(TagKey<T> tagKey) {
        return getRegistryOf(tagKey).getEntryList(tagKey);
    }

    public static <T> Optional<RegistryEntry<T>> tryGetEntry(Registry<T> registry, T object) {
        return registry.getKey(object).map(registry::entryOf);
    }

    public static <T> boolean isObjectInTag(Registry<T> registry, Identifier tagId, T object) {
        return tryGetTagKey(registry, tagId).map(tagKey -> isObjectInTag(registry, tagKey, object)).orElse(false);
    }

    public static <T> boolean isObjectInTag(Registry<T> registry, TagKey<T> tag, T object) {
        var entry = tryGetEntry(registry, object);
        return entry.map(tRegistryEntry -> tRegistryEntry.isIn(tag)).orElse(false);
    }

    public static <T> boolean isTagEmpty(TagKey<T> tag) {
        return getEntries(tag).map(RegistryEntryList::size).orElse(0) == 0;
    }

    @SuppressWarnings("unchecked")
    public static <T> Registry<T> getRegistryOf(@NotNull TagKey<T> key) {
        return (Registry<T>) Registries.REGISTRIES.get(key.registry().getValue());
    }
}
