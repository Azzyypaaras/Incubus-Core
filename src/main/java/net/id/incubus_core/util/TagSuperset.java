package net.id.incubus_core.util;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.tag.ServerTagManagerHolder;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TagSuperset<T> {

    private final List<Identifier> identifiableTags;
    private final Object2ObjectMap<String, List<?>> additionalDataMap = new Object2ObjectOpenHashMap<>();
    private final RegistryKey<Registry<T>> registryKey;

    public TagSuperset(RegistryKey<Registry<T>> registryKey, Identifier ... ids) {
        this.registryKey = registryKey;
        this.identifiableTags = Collections.unmodifiableList(Arrays.asList(ids));
    }

    @SafeVarargs
    public TagSuperset(RegistryKey<Registry<T>> registryKey, Identifier[] ids, Pair<String, List<?>> ... additionalData) {
        this.registryKey = registryKey;
        this.identifiableTags = Collections.unmodifiableList(Arrays.asList(ids));
        for (Pair<String, List<?>> entry : additionalData) {
            String key = entry.getLeft();
            List<?> data = entry.getRight();

            if(data.size() != identifiableTags.size()) {
                throw new IllegalArgumentException("Data mismatch found under " + key + ", Additional data must form a one to one match with tags");
            }

            additionalDataMap.put(key, data);
        }
    }

    public boolean contains(T item) {
        for (Identifier tagId : identifiableTags) {
            Tag<T> tag = ServerTagManagerHolder.getTagManager().getOrCreateTagGroup(registryKey).getTagOrEmpty(tagId);
            if(tag.contains(item))
                return true;
        }
        return false;
    }

    public Identifier getTagId(int i) {
        return identifiableTags.get(i);
    }

    public <K> K getAdditionalData(Class<K> clazz, String key, int i) {
        return clazz.cast(additionalDataMap.get(key).get(i));
    }

    public int getIndex(Identifier tagId) {
        return identifiableTags.indexOf(tagId);
    }
}
