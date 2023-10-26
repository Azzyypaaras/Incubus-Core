package net.id.incubus_core.recipe.matchbook;

public class IncubusMatches {

    public static void init() {
        MatchRegistry.registerInternal(IntMatch.TYPE, new IntMatch.Factory());
        MatchRegistry.registerInternal(IntRangeMatch.TYPE, new IntRangeMatch.Factory());
        MatchRegistry.registerInternal(FloatMatch.TYPE, new FloatMatch.Factory());
        MatchRegistry.registerInternal(LongMatch.TYPE, new LongMatch.Factory());
        MatchRegistry.registerInternal(ShortMatch.TYPE, new ShortMatch.Factory());
        MatchRegistry.registerInternal(ByteMatch.TYPE, new ByteMatch.Factory());
        MatchRegistry.registerInternal(StringMatch.TYPE, new StringMatch.Factory());
        MatchRegistry.registerInternal(BooleanMatch.TYPE, new BooleanMatch.Factory());
        MatchRegistry.registerInternal(EnchantmentMatch.TYPE, new EnchantmentMatch.Factory());
    }
}
