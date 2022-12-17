package net.id.incubus_core.misc.item;

import net.id.incubus_core.misc.IncubusSounds;
import net.id.incubus_core.misc.WorthinessChecker;
import net.id.incubus_core.util.FoxDuck;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.FoxEntity.Type;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class BerryBranchItem extends Item {

    public BerryBranchItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!WorthinessChecker.isPlayerWorthy(user.getUuid(), Optional.of(user))) {
            user.sendMessage(Text.translatable("the sacred grove's magic heeds not your will").styled(style -> style.withColor(0xff6b97)), true);
            return ActionResult.PASS;
        }

        if (entity instanceof FoxEntity fox) {
            var duck = (FoxDuck) fox;
            duck.addTrustedUUID(user.getUuid());

            fox.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, Integer.MAX_VALUE, 9, false, false, false));
            fox.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 2, false, false, false));
            fox.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, Integer.MAX_VALUE, 1, false, false, false));
            fox.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, Integer.MAX_VALUE, 1, false, false, false));

            fox.heal(300F);

            fox.setCustomName(Text.translatable("Sagacious Fox").styled(style -> style.withColor(0xedf6ff)));

            if (user.isSneaking()) {
                var color = duck.getFoxColor();
                duck.setFoxColor(color == FoxEntity.Type.RED ? FoxEntity.Type.SNOW : FoxEntity.Type.RED);
                fox.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, 0.5F, 1);
            }

            fox.playSound(IncubusSounds.AHH, 0.5F, 2);
        }
        else if (entity instanceof WolfEntity wolf) {
            if (!wolf.isTamed()) {
                wolf.setOwner(user);
            }

            wolf.heal(50);
        }
        else {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 2, false, true, true));
            entity.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, 0.5F, 1);

        }

        return ActionResult.success(user.getWorld().isClient());
    }

    public Text getName() {
        var name = super.getName();
        return name.getWithStyle(name.getStyle().withColor(0xff6b97)).get(0);
    }

    @Override
    public Text getName(ItemStack stack) {
        var name = super.getName(stack);
        return name.getWithStyle(name.getStyle().withColor(0xff6b97)).get(0);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("§f§oFor The Worthy"));
        tooltip.add(Text.literal("§osprig of endless nourishment").styled(style -> style.withColor(0xff6b97)));
        tooltip.add(Text.literal("§ofoxes love it!").styled(style -> style.withColor(0xff6b97)));
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.eatFood(world, stack.copy());
        return stack;
    }
}
