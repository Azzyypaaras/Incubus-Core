package net.id.incubus_core.systems;

import net.minecraft.util.math.Direction;

@SuppressWarnings("unused")
public interface MaterialProvider {

    Material getMaterial(Direction direction);
}
