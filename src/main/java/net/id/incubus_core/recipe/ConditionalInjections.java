package net.id.incubus_core.recipe;

public interface ConditionalInjections {

    default boolean supportsConditionals() {
        return true;
    }
}
