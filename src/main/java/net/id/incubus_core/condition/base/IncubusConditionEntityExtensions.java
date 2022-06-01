package net.id.incubus_core.condition.base;

import net.id.incubus_core.condition.IncubusCondition;

public interface IncubusConditionEntityExtensions {
    /**
     * @return The condition manager of this entity
     */
    // Should probably have "incubusCore$" at the beginning, but also, uh, I like this better. Sue me
    default ConditionManager getConditionManager() {
        return IncubusCondition.CONDITION_MANAGER_KEY.get(this);
    }
}
