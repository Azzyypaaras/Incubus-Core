package net.id.incubus_core.blocklikeentities.util;

/**
 * An interface for entities that need to be ticked after the main tick.
 */
public interface PostTickEntity {
    void postTick();
}