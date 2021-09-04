package net.id.incubus_core.recipe.processor;

import org.jetbrains.annotations.NotNull;

public abstract class NamedComponent<C> implements ProcessorComponent<C> {

    protected final String componentName;

    protected NamedComponent(String componentName) {
        this.componentName = componentName;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @NotNull
    @Override
    public String getComponentName() {
        return componentName;
    }
}
