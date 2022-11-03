package net.id.incubus_core.recipe.matchbook;

public class IncubusMatches {

    public static void init() {
        MatchRegistry.registerInternal("int", new IntMatch.Factory());
        MatchRegistry.registerInternal("intRange", new IntRangeMatch.Factory());
        MatchRegistry.registerInternal("float", new FloatMatch.Factory());
        MatchRegistry.registerInternal("long", new LongMatch.Factory());
        MatchRegistry.registerInternal("short", new ShortMatch.Factory());
        MatchRegistry.registerInternal("byte", new ByteMatch.Factory());
        MatchRegistry.registerInternal("string", new StringMatch.Factory());
        MatchRegistry.registerInternal("boolean", new BooleanMatch.Factory());
    }
}
