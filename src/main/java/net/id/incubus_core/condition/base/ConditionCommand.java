package net.id.incubus_core.condition.base;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.id.incubus_core.IncubusCore;
import net.id.incubus_core.condition.IncubusCondition;
import net.id.incubus_core.condition.api.Condition;
import net.id.incubus_core.condition.api.ConditionManager;
import net.id.incubus_core.condition.api.Persistence;
import net.id.incubus_core.condition.api.Severity;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ConditionCommand {

    public static final ConditionSuggester CONDITION_SUGGESTER = new ConditionSuggester();
    public static final SeveritySuggester SEVERITY_SUGGESTER = new SeveritySuggester();
    public static final PersistenceSuggester PERSISTENCE_SUGGESTER = new PersistenceSuggester();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal(IncubusCore.MODID + ":condition")
                        .requires((source) -> source.hasPermissionLevel(2))
                        .then(literal("query")
                                .executes(context -> printCondition(context.getSource(), null, null))
                                .then(argument("target", EntityArgumentType.entities())
                                        .executes(context -> printCondition(context.getSource(), EntityArgumentType.getEntities(context, "target"), null))
                                        .then(argument("condition", IdentifierArgumentType.identifier()).suggests(CONDITION_SUGGESTER)
                                                .executes((context -> printCondition(context.getSource(), EntityArgumentType.getEntities(context, "target"), IdentifierArgumentType.getIdentifier(context, "condition")))))))
                        .then(literal("assign")
                                .then(argument("target", EntityArgumentType.entities())
                                        .then(argument("condition", IdentifierArgumentType.identifier()).suggests(CONDITION_SUGGESTER)
                                                .then(argument("persistence", StringArgumentType.word()).suggests(PERSISTENCE_SUGGESTER)
                                                        .then(argument("value", FloatArgumentType.floatArg()).suggests(SEVERITY_SUGGESTER)
                                                                .executes(context -> setCondition(context.getSource(), EntityArgumentType.getEntity(context, "target"), IdentifierArgumentType.getIdentifier(context, "condition"), FloatArgumentType.getFloat(context, "value"), StringArgumentType.getString(context, "persistence"))))))))
                        .then(literal("clear")
                                .executes(context -> clearCondition(context.getSource(), null, null))
                                .then(argument("target", EntityArgumentType.entities())
                                        .executes(context -> clearCondition(context.getSource(), EntityArgumentType.getEntities(context, "target"), null))
                                        .then(argument("condition", IdentifierArgumentType.identifier()).suggests(CONDITION_SUGGESTER)
                                                .executes(context -> clearCondition(context.getSource(), EntityArgumentType.getEntities(context, "target"), IdentifierArgumentType.getIdentifier(context, "condition"))))))
        );
    }

    private static int clearCondition(ServerCommandSource source, Collection<? extends Entity> entities, Identifier attributeId) {
        entities = handleNullEntity(source, entities);
        entities.forEach(entity -> {
            if(entity instanceof LivingEntity target) {
                var conditions = handleNullCondition(source, attributeId, target);
                if (!conditions.isEmpty()) {
                    conditions.forEach(condition -> {
                        ConditionManager manager = target.getConditionManager();
                        manager.set(condition, Persistence.TEMPORARY, 0);
                        manager.set(condition, Persistence.CHRONIC, 0);
                        manager.trySync();
                    });

                    source.sendFeedback(
                            new TranslatableText(
                                    "commands.incubus_core.condition.success.clear.individual",
                                    conditions.size(), entity.getDisplayName()
                            ),
                            true
                    );
                }
            }
        });
        source.sendFeedback(new TranslatableText("commands.incubus_core.condition.success.clear"), true);
        return 1;
    }

    private static int printCondition(ServerCommandSource source, @Nullable Collection<? extends Entity> entities, Identifier attributeId) {
        entities = handleNullEntity(source, entities);
        entities.forEach(entity -> {
            if(entity instanceof LivingEntity target) {
                var conditions = handleNullCondition(source, attributeId, target);
                conditions.forEach(condition -> {
                    var rawSeverity = target.getConditionManager().getScaledSeverity(condition);
                    var severity = Severity.getSeverity(rawSeverity);

                    if (condition.isApplicableTo(target)) {
                        // todo: also print who is being queried
                        source.sendFeedback(new TranslatableText("commands.incubus_core.condition.success.query", new TranslatableText(condition.getTranslationKey()), new TranslatableText(severity.getTranslationKey()), rawSeverity), false);
                    } else {
                        source.sendError(new TranslatableText("commands.incubus_core.condition.failure.query", new TranslatableText(condition.getTranslationKey())));
                    }
                });
            }
        });
        return 1;
    }

    private static int setCondition(ServerCommandSource source, Entity entity, Identifier attributeId, float value, String persistenceString) {
        if(entity instanceof LivingEntity target) {
            Condition condition;
            Persistence persistence;

            try {
                condition = Condition.getOrThrow(attributeId);
            } catch (NoSuchElementException e) {
                source.sendError(new TranslatableText("commands.incubus_core.condition.failure.get_condition", attributeId));
                return 1;
            }
            try {
                persistence = Persistence.valueOf(persistenceString);
            } catch (NoSuchElementException e) {
                source.sendError(new TranslatableText("commands.incubus_core.condition.failure.get_persistence", persistenceString));
                return 1;
            }

            var manager = target.getConditionManager();

            if(condition.isApplicableTo(target)) {
                if(manager.set(condition, persistence, value)) {
                    var rawSeverity = manager.getScaledSeverity(condition);
                    var severity = Severity.getSeverity(rawSeverity);

                    // todo: also print who the condition is being assigned to
                    source.sendFeedback(new TranslatableText("commands.incubus_core.condition.success.assign", new TranslatableText(condition.getTranslationKey()), new TranslatableText(severity.getTranslationKey()), rawSeverity), false);
                    manager.trySync();
                }
                else {
                    source.sendError(new TranslatableText("commands.incubus_core.condition.failure.assign"));
                }
            }
        }
        return 1;
    }

    private static Collection<? extends Entity> handleNullEntity(ServerCommandSource source, Collection<? extends Entity> entities){
        if (entities == null) {
            try {
                entities = List.of(source.getEntityOrThrow());
            } catch (Exception e) {
                source.sendError(new TranslatableText("commands.incubus_core.condition.failure.get_entity"));
                entities = List.of();
            }
        }
        return entities;
    }

    private static Collection<Condition> handleNullCondition(ServerCommandSource source, Identifier attributeId, LivingEntity target){
        Collection<Condition> conditions;
        if (attributeId != null) {
            try {
                conditions = List.of(Condition.getOrThrow(attributeId));
            } catch (NoSuchElementException e) {
                source.sendError(new TranslatableText("commands.incubus_core.condition.failure.get_condition", attributeId));
                conditions = List.of();
            }
        } else {
            conditions = Condition.getValidConditions(target.getType());
        }
        return conditions;
    }

    public static class ConditionSuggester implements SuggestionProvider<ServerCommandSource> {
        @Override
        public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
            IncubusCondition.CONDITION_REGISTRY.getIds().forEach(id -> builder.suggest(id.toString()));
            return builder.buildFuture();
        }
    }

    public static class SeveritySuggester implements SuggestionProvider<ServerCommandSource> {
        @Override
        public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
            Condition condition;

            try {
                condition = Condition.getOrThrow(IdentifierArgumentType.getIdentifier(context, "condition"));
            } catch (Exception e){
                return builder.suggest(0).buildFuture();
            }

            float max = condition.scalingValue;

            Arrays.stream(Severity.values()).sorted().forEach((severity) -> builder.suggest(Float.toString(Math.round(max * severity.triggerPercent))));
            builder.suggest(Float.toString(max));

            return builder.buildFuture();
        }
    }

    public static class PersistenceSuggester implements SuggestionProvider<ServerCommandSource> {
        @Override
        public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
            builder.suggest(Persistence.TEMPORARY.name());
            builder.suggest(Persistence.CHRONIC.name());
            return builder.buildFuture();
        }
    }
}
