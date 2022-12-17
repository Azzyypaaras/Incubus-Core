package net.id.incubus_core.misc.item;

import net.id.incubus_core.misc.IncubusSounds;
import net.id.incubus_core.misc.WorthinessChecker;
import net.id.incubus_core.util.FoxDuck;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class FoxEffigyItem extends Item {

    public FoxEffigyItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var user = context.getPlayer();
        var world = context.getWorld();
        var pos = context.getBlockPos().up();

        if (user == null)
            return ActionResult.FAIL;

        if (!WorthinessChecker.isPlayerWorthy(user.getUuid(), Optional.of(user))) {
            WorthinessChecker.smite(user);
            return ActionResult.FAIL;
        }

        var fox = new FoxEntity(EntityType.FOX, world);
        fox.setPosition(Vec3d.ofCenter(pos));
        ((FoxDuck) fox).setFoxColor(FoxEntity.Type.SNOW);
        ((FoxDuck) fox).addTrustedUUID(user.getUuid());
        fox.equipStack(EquipmentSlot.MAINHAND, new ItemStack(IncubusCoreItems.BERRY_BRANCH));

        fox.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, Integer.MAX_VALUE, 9, false, false, false));
        fox.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 9, false, false, false));
        fox.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, Integer.MAX_VALUE, 9, false, false, false));
        fox.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, Integer.MAX_VALUE, 9, false, false, false));

        fox.heal(300F);

        fox.setCustomName(Text.translatable("Divine Fox").styled(style -> style.withColor(0xedf6ff)));
        world.spawnEntity(fox);

        fox.playSound(IncubusSounds.AHH, 0.5F, 2);

        return ActionResult.CONSUME;
    }

    public Text getName() {
        var name = super.getName();
        return name.getWithStyle(name.getStyle().withColor(0xedf6ff)).get(0);
    }

    @Override
    public Text getName(ItemStack stack) {
        var name = super.getName(stack);
        return name.getWithStyle(name.getStyle().withColor(0xedf6ff)).get(0);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("§f§oFor The Worthy"));
        tooltip.add(Text.translatable("§oa thieving spirit").styled(style -> style.withColor(0xedf6ff)));
        tooltip.add(Text.translatable("§ocast from the sacred grove").styled(style -> style.withColor(0xedf6ff)));
        tooltip.add(Text.translatable("§otook a sprig of the foxthorn").styled(style -> style.withColor(0xedf6ff)));
        tooltip.add(Text.translatable("§ovenerable mother of all berries").styled(style -> style.withColor(0xedf6ff)));
        tooltip.add(Text.translatable("§oa relic is not given up easily").styled(style -> style.withColor(0xedf6ff)));
        tooltip.add(Text.translatable("§oan equal trade may be accepted").styled(style -> style.withColor(0xedf6ff)));
        tooltip.add(Text.translatable("§osomething magical").styled(style -> style.withColor(0xedf6ff)));
        tooltip.add(Text.translatable("§oshattering the chains of life").styled(style -> style.withColor(0xedf6ff)));
        super.appendTooltip(stack, world, tooltip, context);
    }
}