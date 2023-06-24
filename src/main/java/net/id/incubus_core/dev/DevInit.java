package net.id.incubus_core.dev;

import net.fabricmc.fabric.api.item.v1.*;
import net.fabricmc.fabric.api.object.builder.v1.block.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.*;
import net.fabricmc.loader.api.*;
import net.id.incubus_core.*;
import net.id.incubus_core.condition.*;
import net.id.incubus_core.condition.api.*;
import net.id.incubus_core.dev.block.*;
import net.id.incubus_core.dev.item.*;
import net.id.incubus_core.dev.recipe.*;
import net.id.incubus_core.util.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.client.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.*;

import static net.id.incubus_core.IncubusCore.*;

/**
 * Development only stuff.
 * <p>
 * Used for testing internal things without littering production environments.
 */
public final class DevInit {
    static {
        if (!FabricLoader.getInstance().isDevelopmentEnvironment()) {
            throw new RuntimeException("Incubus Core development classes loaded in production, this should not happen!");
        }
    }

    private static final Item ENTITY_DEATH_MESSAGE_ITEM = new EntityDeathMessageTestItem(new FabricItemSettings());

    private static final Block TEST_FURNACE_BLOCK = new TestFurnaceBlock(FabricBlockSettings.copyOf(Blocks.OBSIDIAN));
    private static final Item TEST_FURNACE_BLOCKITEM = new BlockItem(TEST_FURNACE_BLOCK, new FabricItemSettings());
    public static final BlockEntityType<TestFurnaceBlockEntity> TEST_FURNACE_BLOCK_ENTITY_TYPE = FabricBlockEntityTypeBuilder.create(TestFurnaceBlockEntity::new, TEST_FURNACE_BLOCK).build();
    public static final RecipeType<TestRecipeType> TEST_RECIPE_TYPE = RecipeType.register("incubus_core:test_recipe_type");
    public static final RecipeSerializer<TestRecipeType> TEST_RECIPE_SERIALIZER = new TestRecipeType.Serializer();

    private static final Condition TEST_CONDITION = new Condition(TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier("a")), 100, 100, 5, 5, 1, 50) {
        @Override
        public void tick(World world, LivingEntity entity, Severity severity, float rawSeverity) {
        }

        @Override
        public void tickPlayer(World world, PlayerEntity player, Severity severity, float rawSeverity) {
            if (severity.isAsOrMoreSevere(Severity.MILD)) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 10));
            }
        }

        @Override
        public void clientTick(ClientWorld world, LivingEntity entity, Severity severity, float rawSeverity) {
        }
    };

    public static void commonInit() {
        // This should catch any mixin errors that occur later in runtime.
        // This also requires incubus_core.devtools=true
        if (Config.getBoolean(locate("mixin_audit"), false)) {
            LOG.info("Running mixin audit because we are in debug mode...");
            MixinEnvironment.getCurrentEnvironment().audit();
        }
    
        registerItem("entity_death_message_item", ENTITY_DEATH_MESSAGE_ITEM);
        registerBlock("test_furnace", TEST_FURNACE_BLOCK);
        registerItem("test_furnace", TEST_FURNACE_BLOCKITEM);
        registerBE("test_furnace", TEST_FURNACE_BLOCK_ENTITY_TYPE);
        Registry.register(Registries.RECIPE_SERIALIZER, IncubusCore.locate("test_recipe_type"), TEST_RECIPE_SERIALIZER);
    }

    public static void clientInit() {
        Registry.register(IncubusCondition.CONDITION_REGISTRY, IncubusCore.locate("test_condition"), TEST_CONDITION);
    }
}
