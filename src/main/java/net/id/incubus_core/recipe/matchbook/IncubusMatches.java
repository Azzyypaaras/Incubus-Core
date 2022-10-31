package net.id.incubus_core.recipe.matchbook;

public class IncubusMatches {

    public static void init() {
        MatchRegistry.registerInternal("int", new IntMatch.Factory());
        MatchRegistry.registerInternal("intRange", new IntMatch.Factory());
        MatchRegistry.registerInternal("float", new IntMatch.Factory());
        MatchRegistry.registerInternal("string", new IntMatch.Factory());
    }
}
