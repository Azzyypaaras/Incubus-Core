package net.id.incubus_core.util;

import net.minecraft.entity.passive.FoxEntity;

import java.util.UUID;

public interface FoxDuck {

    FoxEntity.Type getFoxColor();

    void setFoxColor(FoxEntity.Type type);

    void addTrustedUUID(UUID id);
}
