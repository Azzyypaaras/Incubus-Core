package net.id.incubus_core.misc.item;

import net.id.incubus_core.misc.*;
import net.minecraft.block.*;
import net.minecraft.client.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.particle.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class AzzysFlagItem extends HoeItem {
	
	private static final ArrayList<Block> SAPLINGS;
	
	public AzzysFlagItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
		super(toolMaterial, attackDamage, attackSpeed, settings);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (!WorthinessChecker.isPlayerWorthy(user.getUuid(), Optional.of(user))) {
			WorthinessChecker.smite(user);
		}
		
		var random = world.getRandom();
		var flagStaff = user.getStackInHand(hand);
		
		if (user.isSneaking() && hand == Hand.OFF_HAND) {
			cycleMode(flagStaff);
			user.sendMessage(getMode(flagStaff).name, true);
			world.playSoundFromEntity(null, user, IncubusSounds.AHH, SoundCategory.PLAYERS, 1F, 1.4F + random.nextFloat() * 0.5F);
			return TypedActionResult.success(flagStaff, world.isClient());
		}
		
		var mode = getMode(flagStaff);
		var sneaking = user.isSneaking();
		
		switch (mode) {
			case MUNDANE -> {
				
				return TypedActionResult.pass(flagStaff);
				
			}
			case GIFT -> {
				
				var players = new ArrayList<>(world.getPlayers());
				players.remove(user);
				
				if (players.isEmpty()) {
					handleGifting(null, random, user, sneaking, true);
					return TypedActionResult.success(flagStaff, world.isClient());
				}
				
				Collections.shuffle(players);
				var target = players.get(0);
				
				handleGifting(target, random, user, sneaking, false);
				world.playSoundFromEntity(null, sneaking ? user : target, random.nextFloat() < 0.015F ? IncubusSounds.APYR : IncubusSounds.BAD_TO_THE_BONE, SoundCategory.PLAYERS, 1, random.nextFloat() * 2);
				
				return TypedActionResult.success(flagStaff, world.isClient());
				
			}
			case VANISH -> {
				
				var playerData = IncubusPlayerData.get(user);
				var invisible = playerData.shouldSkipRender();
				
				playerData.setBlockRendering(!invisible);
				world.playSoundFromEntity(null, user, IncubusSounds.DRIP, SoundCategory.PLAYERS, 1, 1);
				
				if (!world.isClient()) {
					IncubusPlayerData.PLAYER_DATA_KEY.sync(user);
				}
				
				return TypedActionResult.success(flagStaff, world.isClient());
				
			}
			case LIFE -> {
				
				if (sneaking) {
					handleSpawning(world, random, user);
					world.playSoundFromEntity(null, user, IncubusSounds.AHH, SoundCategory.PLAYERS, 1, 1);
					
					return TypedActionResult.success(flagStaff, world.isClient());
				}
				
			}
			case SANS -> {
				var players = new ArrayList<>(world.getPlayers());
				players.remove(user);
				
				if (players.isEmpty())
					return TypedActionResult.fail(flagStaff);
				
				Collections.shuffle(players);
				var target = players.get(0);
				
				world.playSound(null, user.getBlockPos(), IncubusSounds.DRIP, SoundCategory.PLAYERS, 0.9F, 1);
				if (!world.isClient()) {
					Box bounds = user.getBoundingBox();
					for (int j = 0; j < Math.pow(bounds.getAverageSideLength() * 3, 2); j++) {
						((ServerWorld) world).spawnParticles(ParticleTypes.END_ROD, user.getX() + (random.nextDouble() * bounds.getXLength() - bounds.getXLength() / 2), user.getY() + (random.nextDouble() * bounds.getYLength()), user.getZ() + (random.nextDouble() * bounds.getZLength() - bounds.getZLength() / 2), random.nextInt(4), 0, 0, 0, 0.9);
					}
				}
				
				user.teleport(target.getX(), target.getY(), target.getZ());
				world.playSoundFromEntity(null, target, IncubusSounds.APYR, SoundCategory.PLAYERS, 0.65F, 1);
				
				return TypedActionResult.success(flagStaff, world.isClient());
			}
		}
		
		
		return TypedActionResult.fail(flagStaff);
	}
	
	private void handleGifting(PlayerEntity target, Random random, PlayerEntity user, boolean sneaking, boolean hardFail) {
		if (user.getWorld().isClient())
			return;
		
		if (sneaking) {
			int roll = random.nextInt(7);
			switch (roll) {
				case 0:
					user.giveItemStack(new ItemStack(Items.SWEET_BERRIES));
				case 1:
					user.giveItemStack(new ItemStack(Items.ROSE_BUSH));
				case 2:
					user.giveItemStack(new ItemStack(Items.VINE));
				case 3:
					user.giveItemStack(new ItemStack(Items.DARK_OAK_SAPLING));
				case 4:
					user.giveItemStack(new ItemStack(Items.SPRUCE_SAPLING));
				case 5:
					user.giveItemStack(new ItemStack(IncubusCoreItems.LONG_SPATULA));
				case 6:
					user.giveItemStack(new ItemStack(IncubusCoreItems.FOX_EFFIGY));
			}

		} else if(!hardFail) {
			var cap = lowRollRandom(random, 5) + 1;
			target.sendMessage(Text.translatable("You have been graced with gifts!").styled(style -> style.withColor(0xffb0b3)), true);
			for (int i = 0; i < cap; i++) {
				float roll = random.nextInt(100);
				
				if (roll > 98) {
					grantStack(target, IncubusCoreItems.DEBUG_FLAME_ITEM, random.nextInt(65));
				} else if (roll > 95) {
					grantStack(target, IncubusCoreItems.FOX_EFFIGY, 1);
				} else if (roll > 90) {
					grantStack(target, IncubusCoreItems.LONG_SPATULA, 1);
				} else if (roll > 80) {
					grantStack(target, IncubusCoreItems.MOBILK_1, lowRollRandom(random, 9));
				} else if (roll > 70) {
					grantStack(target, IncubusCoreItems.LEAN, lowRollRandom(random, 9));
				} else if (roll > 60) {
					grantStack(target, IncubusCoreItems.RAT_POISON, lowRollRandom(random, 9));
				} else if (roll > 50) {
					grantStack(target, Items.CAKE, 1);
				} else if (roll > 30) {
					grantStack(target, Items.FURNACE_MINECART, 1);
				} else {
					int subRoll = random.nextInt(7);
					int count = lowRollRandom(random, 65);
					
					Item item = switch (subRoll) {
						case 0 -> Items.SWEET_BERRIES;
						case 1 -> Items.ROOTED_DIRT;
						case 2 -> Items.POPPY;
						case 3 -> Items.BIG_DRIPLEAF;
						case 4 -> Items.SMALL_DRIPLEAF;
						case 5 -> Items.COOKIE;
						case 6 -> Items.POISONOUS_POTATO;
						default -> throw new IllegalStateException("Unexpected value: " + subRoll);
					};
					
					grantStack(target, item, count);
				}
			}
		}
	}
	
	private void handleSpawning(World world, Random random, PlayerEntity user) {
		var centerPos = user.getBlockPos();
		var tries = random.nextInt(19);
		
		for (int i = 0; i < tries; i++) {
			var spawnPos = centerPos.add(random.nextInt(11) - 5, random.nextInt(7) - 3, random.nextInt(11) - 5);
			var variant = random.nextFloat();
			
			Entity entity = null;
			
			if (world.isWater(spawnPos)) {
				if (variant > 0.9) {
					entity = new AxolotlEntity(EntityType.AXOLOTL, world);
				} else if (variant > 0.6) {
					entity = new TropicalFishEntity(EntityType.TROPICAL_FISH, world);
				} else if (variant > 0.3) {
					entity = new SalmonEntity(EntityType.SALMON, world);
				} else {
					entity = new CodEntity(EntityType.COD, world);
				}
			} else if (world.isAir(spawnPos)) {
				if (world.isAir(spawnPos.down())) {
					if (variant > 0.9) {
						entity = new BatEntity(EntityType.BAT, world);
					} else if (variant > 0.75) {
						entity = new ChickenEntity(EntityType.CHICKEN, world);
					} else {
						entity = new BeeEntity(EntityType.BEE, world);
					}
				} else {
					
					if (variant > 0.98) {
						entity = new MooshroomEntity(EntityType.MOOSHROOM, world);
					} else if (variant > 0.92) {
						entity = new WolfEntity(EntityType.WOLF, world);
					} else if (variant > 0.725) {
						entity = new FoxEntity(EntityType.FOX, world);
					} else {
						entity = new RabbitEntity(EntityType.RABBIT, world);
					}
				}
			}
			
			if (entity != null) {
				entity.setPosition(Vec3d.ofCenter(spawnPos));
				world.spawnEntity(entity);
				if (!world.isClient()) {
					Box bounds = entity.getBoundingBox();
					for (int j = 0; j < Math.pow(bounds.getAverageSideLength() * 3, 2); j++) {
						((ServerWorld) world).spawnParticles(DustParticleEffect.DEFAULT, entity.getX() + (random.nextDouble() * bounds.getXLength() - bounds.getXLength() / 2), entity.getY() + (random.nextDouble() * bounds.getYLength()), entity.getZ() + (random.nextDouble() * bounds.getZLength() - bounds.getZLength() / 2), random.nextInt(4), 0, 0, 0, 0.9);
					}
				}
			}
		}
		
		
	}
	
	private void grantStack(PlayerEntity target, Item item, int count) {
		target.giveItemStack(new ItemStack(item, count));
	}
	
	private int lowRollRandom(Random random, int cap) {
		return Math.min(random.nextInt(cap), random.nextInt(cap));
	}
	
	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity target, Hand hand) {
		var mode = getMode(stack);
		var world = user.getWorld();
		var random = user.getRandom();
		
		if (target instanceof PlayerEntity player) {
			switch (mode) {
				case GIFT: {
					handleGifting(player, random, user, false, false);
					return ActionResult.success(world.isClient());
				}
				case BLESS: {
					target.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 3));
					target.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 6000, 4));
					target.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 6000, 4));
					
					world.playSoundFromEntity(null, target, IncubusSounds.DRIP, SoundCategory.PLAYERS, 1, 1);
					return ActionResult.success(world.isClient());
				}
				case CURSE: {
					if (!user.isSneaking()) {
						target.damage(IncubusDamageSources.unworthy(world), 10F);
						world.playSoundFromEntity(null, target, IncubusSounds.WEAK, SoundCategory.PLAYERS, 1, 0.9F + random.nextFloat() * 0.2F);
					} else {
						if (!world.isClient()) {
							var lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
							lightning.setChanneler((ServerPlayerEntity) user);
							lightning.setPosition(target.getPos());
							
							target.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 200, 3));
							target.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 6000, 2));
							target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 6000, 2));
							target.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 6000, 2));
							
							world.spawnEntity(lightning);
							world.playSoundFromEntity(null, target, IncubusSounds.WEAK, SoundCategory.PLAYERS, 1, 1F);
						}
					}
					return ActionResult.success(world.isClient());
					
				}
				case LIFE: {
					target.clearStatusEffects();
					target.heal(500f);
					target.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 20, 0));
					target.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 200, 3));
					
					world.playSoundFromEntity(null, target, IncubusSounds.AHH, SoundCategory.PLAYERS, 0.5F, 1F);
					return ActionResult.success(world.isClient());
				}
				case WORTHY: {
					var playerData = IncubusPlayerData.get(player);
					var worthy = playerData.isDeemedWorthy();
					
					playerData.deemWorthy(!playerData.isDeemedWorthy());
					
					if (!worthy) {
						player.sendMessage(Text.translatable("You have been deemed worthy"), true);
						world.playSoundFromEntity(null, target, IncubusSounds.DRIP_LONG, SoundCategory.PLAYERS, 0.5F, 1F);
					} else {
						player.sendMessage(Text.translatable("You have no right!"), true);
						world.playSoundFromEntity(null, target, IncubusSounds.WEAK, SoundCategory.PLAYERS, 0.5F, 1F);
					}
					
					if (!world.isClient()) {
						IncubusPlayerData.PLAYER_DATA_KEY.sync(player);
					}
					
					return ActionResult.success(world.isClient());
					
				}
				default:
					return ActionResult.PASS;
			}
			
		}
		
		return ActionResult.PASS;
	}
	
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		var pos = context.getBlockPos();
		var world = context.getWorld();
		var mode = getMode(context.getStack());
		var sneaking = Optional.ofNullable(context.getPlayer()).map(PlayerEntity::isSneaking).orElse(false);
		var state = world.getBlockState(pos);
		var block = state.getBlock();
		
		if (mode == Mode.MUNDANE) {
			if (sneaking) {
				if (context.getSide() == Direction.DOWN) {
					return ActionResult.PASS;
				} else {
					PlayerEntity playerEntity = context.getPlayer();
					BlockState blockState2 = PathStateAccessor.getPathStates().get(state.getBlock());
					BlockState blockState3 = null;
					if (blockState2 != null && world.getBlockState(pos.up()).isAir()) {
						world.playSound(playerEntity, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
						blockState3 = blockState2;
					} else if (state.getBlock() instanceof CampfireBlock && (Boolean) state.get(CampfireBlock.LIT)) {
						if (!world.isClient()) {
							world.syncWorldEvent(null, 1009, pos, 0);
						}
						
						CampfireBlock.extinguish(context.getPlayer(), world, pos, state);
						blockState3 = state.with(CampfireBlock.LIT, false);
					}
					
					if (blockState3 != null) {
						if (!world.isClient) {
							world.setBlockState(pos, blockState3, 11);
							if (playerEntity != null) {
								context.getStack().damage(1, playerEntity, (p) -> {
									p.sendToolBreakStatus(context.getHand());
								});
							}
						}
						
						return ActionResult.success(world.isClient);
					} else {
						return ActionResult.PASS;
					}
				}
				
			}
			
			return super.useOnBlock(context);
		} else if (mode == Mode.LIFE) {
			BoneMealItem.createParticles(world, pos, 0);
			
			if (SAPLINGS.contains(block)) {
				if (block == Blocks.FLOWERING_AZALEA) {
					world.setBlockState(pos, Blocks.OAK_SAPLING.getDefaultState());
				} else {
					world.setBlockState(pos, SAPLINGS.get(SAPLINGS.indexOf(block) + 1).getDefaultState());
				}
				
				world.playSound(null, pos, SoundEvents.BLOCK_SHROOMLIGHT_PLACE, SoundCategory.BLOCKS, 1, 1);
			}
			
			if (block instanceof Fertilizable fertilizable && world.getRandom().nextFloat() < 0.1F) {
				if (fertilizable.isFertilizable(world, pos, state, world.isClient()) && !world.isClient())
					fertilizable.grow((ServerWorld) world, world.getRandom(), pos, state);
			}
		}
		
		return ActionResult.FAIL;
	}
	
	
	public static Mode getMode(ItemStack stack) {
		var id = stack.getOrCreateNbt().getString("mode");
		if (id.isEmpty()) {
			stack.getOrCreateNbt().putString("mode", "MUNDANE");
			return Mode.MUNDANE;
		}
		return Mode.valueOf(id);
	}
	
	public static void setMode(ItemStack stack, Mode mode) {
		stack.getOrCreateNbt().putString("mode", mode.name());
	}
	
	public static void cycleMode(ItemStack stack) {
		setMode(stack, getMode(stack).next());
	}
	
	@Override
	public Text getName() {
		var name = super.getName();
		return name.getWithStyle(name.getStyle().withColor(0xffb0b3)).get(0);
	}
	
	@Override
	public Text getName(ItemStack stack) {
		var name = super.getName(stack);
		return name.getWithStyle(name.getStyle().withColor(0xffb0b3)).get(0);
	}
	
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("§f§oFor The Worthy"));
		tooltip.add(Text.translatable("§olady azzy's blushing staff").styled(style -> style.withColor(0xffb0b3)));
		tooltip.add(getMode(stack).name);
		super.appendTooltip(stack, world, tooltip, context);
	}
	
	@Override
	public boolean hasGlint(ItemStack stack) {
		return false;
	}
	
	static {
		SAPLINGS = new ArrayList<>();
		SAPLINGS.add(Blocks.OAK_SAPLING);
		SAPLINGS.add(Blocks.BIRCH_SAPLING);
		SAPLINGS.add(Blocks.SPRUCE_SAPLING);
		SAPLINGS.add(Blocks.DARK_OAK_SAPLING);
		SAPLINGS.add(Blocks.ACACIA_SAPLING);
		SAPLINGS.add(Blocks.JUNGLE_SAPLING);
		SAPLINGS.add(Blocks.AZALEA);
		SAPLINGS.add(Blocks.FLOWERING_AZALEA);
	}
	
	private enum Mode {
		MUNDANE("mundane", 0x9faebd),
		GIFT("charitable", 0xcf91ff),
		BLESS("anointing", 0x70ffcd),
		CURSE("emnity", 0xd83a71),
		VANISH("illusionary", 0xbce5de),
		LIFE("vitalizing", 0xffb5c3),
		SANS("sans", 0xffc552),
		WORTHY("righteous", 0xedfcff);
		
		public final Text name;
		
		Mode(String name, int color) {
			this.name = Text.literal(name).styled(style -> style.withColor(color).withItalic(true));
			
		}
		
		public Mode next() {
			var nextOrdinal = this == WORTHY ? 0 : ordinal() + 1;
			return values()[nextOrdinal];
		}
	}
}
