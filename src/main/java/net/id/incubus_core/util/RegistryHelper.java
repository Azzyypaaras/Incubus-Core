package net.id.incubus_core.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;

import java.util.Optional;

public class RegistryHelper {

    public static <T> Optional<TagKey<T>> searchForTagKey(Registry<T> registry, Identifier id) {
        return registry.streamTags().filter(tag -> tag.id().equals(id)).findAny();
    }

    public static <T> Optional<TagKey<T>> tryGetTagKey(Registry<T> registry, Identifier id) {
        return registry.streamTags().filter(tagKey -> tagKey.id().equals(id)).findFirst();
    }

    @SuppressWarnings("unchecked")
    public static <T> RegistryEntryList<T> getEntries(TagKey<T> tagKey) {
        return ((Registry<T>) Registry.REGISTRIES.get(tagKey.registry().getValue())).getEntryList(tagKey).get();
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<RegistryEntry<T>> tryGetEntry(Registry<T> registry, T object) {
        if(object instanceof Item item) {
            return Optional.ofNullable((RegistryEntry<T>) item.getRegistryEntry());
        }
        if(object instanceof Block block) {
            return Optional.ofNullable((RegistryEntry<T>) block.getRegistryEntry());
        }
        return registry.getKey(object).flatMap(registry::getEntry);
    }

    public static <T> boolean isObjectInTag(Registry<T> registry, Identifier tagId, T object) {
        var tagKey = tryGetTagKey(registry, tagId);
        var entry = tryGetEntry(registry, object);
        return entry.map(tRegistryEntry -> tagKey.map(tRegistryEntry::isIn).orElse(false)).orElse(false);
    }

    public static <T> boolean isTagEmpty(TagKey<T> tag) {
        return getEntries(tag).size() < 1;
    }
}
