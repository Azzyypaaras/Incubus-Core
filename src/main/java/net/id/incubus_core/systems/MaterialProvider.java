package net.id.incubus_core.systems;

import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public interface MaterialProvider {

    Material getMaterial(@Nullable Direction direction);
}
