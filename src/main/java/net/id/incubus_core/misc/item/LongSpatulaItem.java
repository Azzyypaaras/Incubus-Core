package net.id.incubus_core.misc.item;

import net.id.incubus_core.misc.IncubusDamageSources;
import net.id.incubus_core.misc.IncubusPlayerData;
import net.id.incubus_core.misc.IncubusSounds;
import net.id.incubus_core.misc.WorthinessChecker;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class LongSpatulaItem extends ShovelItem {

    public LongSpatulaItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        var random = attacker.getRandom();

        if (target.getType() != EntityType.ENDERMAN && random.nextInt(5) == 0) {
            target.setOnFireFor(4);
            target.playSound(IncubusSounds.BLAST, 0.75F, 0.9F + random.nextFloat() * 0.2F);
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (user.getItemCooldownManager().isCoolingDown(this))
            return ActionResult.FAIL;

        if (!WorthinessChecker.isPlayerWorthy(user.getUuid(), Optional.of(user)))
            user.sendMessage(Text.translatable("you'll never be grillin").styled(style -> style.withColor(0xffc591)), true);

        if (!entity.isOnGround() && entity.hasStatusEffect(StatusEffects.WEAKNESS)) {
            applyRiposteEffects(entity, user);
            user.getItemCooldownManager().set(this, 18);
        }
        else {
            if (entity instanceof PlayerEntity player) {
                IncubusPlayerData.get(player).setParryStateTicks(6);
                user.getItemCooldownManager().set(this, 18);
            }
            else if (entity.getPos().distanceTo(user.getPos()) < entity.getBoundingBox().getAverageSideLength() + 1) {
                applyParryEffects(entity, user);
                user.getItemCooldownManager().set(this, 18);
            }
        }

        return ActionResult.success(user.getWorld().isClient());
    }

    public static void applyRiposteEffects(LivingEntity entity, PlayerEntity attacker) {
        var world = entity.getWorld();
        var random = entity.getRandom();

        entity.clearStatusEffects();
        entity.addVelocity(0, -5 + random.nextFloat() * 5, 0);

        entity.damage(IncubusDamageSources.grillin(attacker), WorthinessChecker.isPlayerWorthy(attacker.getUuid(), Optional.of(attacker)) ? entity.getMaxHealth() / 3F + 15F : 10F);

        attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 40, 13));
        entity.playSound(IncubusSounds.SLAM, 1, 1);
        if (!world.isClient()) {
            Box bounds = entity.getBoundingBox();
            for (int j = 0; j < Math.pow(bounds.getAverageSideLength() * 10, 2); j++) {
                ((ServerWorld) world).spawnParticles(random.nextBoolean() ? ParticleTypes.SMALL_FLAME : ParticleTypes.FLAME, entity.getX() + (random.nextDouble() * bounds.getXLength() - bounds.getXLength() / 2), entity.getY() + (random.nextDouble() * bounds.getYLength()), entity.getZ() + (random.nextDouble() * bounds.getZLength() - bounds.getZLength() / 2), random.nextInt(4), 0, 0, 0, random.nextFloat() * 0.25F);
                if (random.nextBoolean() && random.nextBoolean())
                    ((ServerWorld) world).spawnParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, entity.getX() + (random.nextDouble() * bounds.getXLength() - bounds.getXLength() / 2), entity.getY() + (random.nextDouble() * bounds.getYLength()), entity.getZ() + (random.nextDouble() * bounds.getZLength() - bounds.getZLength() / 2), random.nextInt(4), 0, 0, 0, random.nextFloat() * 0.125F);
            }
        }

        var drops = random.nextInt(13) + 4;

        if(entity.getType() == EntityType.COW) {
            for (int i = 0; i < drops; i++) {
                entity.dropStack(new ItemStack(Items.COOKED_BEEF));
            }
        }
        else if(entity.getType() == EntityType.PIG) {
            for (int i = 0; i < drops; i++) {
                entity.dropStack(new ItemStack(Items.COOKED_PORKCHOP));
            }
        }
        else if(entity.getType() == EntityType.SHEEP) {
            for (int i = 0; i < drops; i++) {
                entity.dropStack(new ItemStack(Items.COOKED_MUTTON));
            }
        }
        else if(entity.getType() == EntityType.CHICKEN) {
            for (int i = 0; i < drops * 1.5; i++) {
                entity.dropStack(new ItemStack(Items.COOKED_CHICKEN));
            }
        }
        else if(entity.getType() == EntityType.RABBIT) {
            for (int i = 0; i < drops * 2; i++) {
                entity.dropStack(new ItemStack(Items.COOKED_RABBIT));
            }
        }
        else if(entity.getType() == EntityType.PILLAGER) {
            for (int i = 0; i < drops; i++) {
                entity.dropStack(new ItemStack(Items.COOKED_PORKCHOP));
            }
        }
    }

    @Override
    public Text getName() {
        var name = super.getName();
        return name.getWithStyle(name.getStyle().withColor(0xffc591)).get(0);
    }

    @Override
    public Text getName(ItemStack stack) {
        var name = super.getName(stack);
        return name.getWithStyle(name.getStyle().withColor(0xffc591)).get(0);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("§f§oFor The Worthy"));
        tooltip.add(Text.literal("§ojust wanna grill, man").styled(style -> style.withColor(0xffc591)));
        super.appendTooltip(stack, world, tooltip, context);
    }

    public static void applyParryEffects(LivingEntity entity, PlayerEntity user) {
        entity.setFireTicks(30);

        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 15, 4));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 15, 4));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 6, 35));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 30, 9));

        entity.takeKnockback(0.45, MathHelper.sin(user.getYaw() * 0.017453292F), -MathHelper.cos(user.getYaw() * 0.017453292F));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 20, 13));

        entity.playSound(IncubusSounds.PARRY, 1, 1);
        entity.timeUntilRegen = 0;
    }
}
