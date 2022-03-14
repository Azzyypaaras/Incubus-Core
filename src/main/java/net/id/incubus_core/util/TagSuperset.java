package net.id.incubus_core.util;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class TagSuperset<T> {

    private final List<Identifier> identifiableTags;
    private final Object2ObjectMap<String, List<?>> additionalDataMap = new Object2ObjectOpenHashMap<>();
    private final Registry<T> registry;

    public TagSuperset(Registry<T> registry, Identifier ... ids) {
        this.registry = registry;
        this.identifiableTags = List.of(ids);
    }

    @SafeVarargs
    public TagSuperset(Registry<T> registry, Identifier[] ids, Pair<String, List<?>> ... additionalData) {
        this.registry = registry;
        this.identifiableTags = List.of(ids);
        for (Pair<String, List<?>> entry : additionalData) {
            String key = entry.getLeft();
            List<?> data = entry.getRight();

            if(data.size() != identifiableTags.size()) {
                throw new IllegalArgumentException("Data mismatch found under " + key + ", Additional data must form a one to one match with tags");
            }

            additionalDataMap.put(key, data);
        }
    }

    public boolean contains(Identifier item) {
        for (Identifier tagId : identifiableTags) {
            if(RegistryHelper.isObjectInTag(registry, tagId, registry.get(item)))
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
