package net.id.incubus_core.systems;

public enum Polarity {
    NEUTRAL(0xf78cff),
    POSITIVE(0x73ffd0),
    NEGATIVE(0xffca57),
    NONE(0xffffff);

    public final int hex;

    Polarity(int hex) {
        this.hex = hex;
    }

    public boolean isValid() {
        return this != NONE;
    }
}
