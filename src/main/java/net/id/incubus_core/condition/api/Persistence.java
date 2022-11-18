package net.id.incubus_core.condition.api;

/**
 * {@code Persistence} is how much a {@code Condition} persists.
 * There are three main types: {@link Persistence#TEMPORARY},
 * {@link Persistence#CHRONIC}, and {@link Persistence#CONSTANT}
 * <br><br>
 * ~ Jack
 * @author AzazelTheDemonLord
 * @see Persistence#TEMPORARY
 * @see Persistence#CHRONIC
 * @see Persistence#CONSTANT
 */
@SuppressWarnings("unused")
public enum Persistence {
    /**
     * This {@code Persistence} is what you'd expect.
     * It's given by things like projectiles, for example, and goes away with time.
     */
    TEMPORARY,
    /**
     * This is similar to the {@link Persistence#TEMPORARY} {@code Persistence}.
     * Depending on the {@code Condition}, it may be reduced only through
     * consumables, or it may just go down slower.
     */
    CHRONIC,
    /**
     * The {@code CONSTANT} {@code Persistence} is given by things like
     * armors and trinkets that apply conditions. It goes away when the
     * armor or trinket is removed.
     */
    CONSTANT;

    /**
     * @return The translation key of this Persistence <br> e.g. {@code "condition.persistence.temporary"}.
     */
    public final String getTranslationKey(){
        return "condition.persistence." + this.name().toLowerCase();
    }
}
