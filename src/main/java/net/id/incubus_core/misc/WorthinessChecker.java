package net.id.incubus_core.misc;

import net.fabricmc.loader.api.FabricLoader;
import net.id.incubus_core.IncubusCore;
import net.id.incubus_core.misc.playerdata.PlayerData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static net.id.incubus_core.IncubusCore.locate;

public class WorthinessChecker {
    private static boolean bypassWorthiness;

    private static final HashMap<UUID, Entry> PLAYER_MAP = new HashMap<>();

    public static boolean isPlayerWorthy(UUID uuid, Optional<PlayerEntity> player) {
        return player.map(IncubusPlayerData::get).map(PlayerData::isDeemedWorthy).orElse(false)
                || Optional.ofNullable(PLAYER_MAP.get(uuid)).map(entry -> entry.worthy).orElse(bypassWorthiness);
    }

    public static CapeType getCapeType(UUID uuid) {
        return Optional.ofNullable(PLAYER_MAP.get(uuid)).map(entry -> entry.capeType).orElse(bypassWorthiness ? CapeType.IMMORTAL : CapeType.NONE);
    }

    private static void putPlayer(UUID id) {
        putPlayer(id, CapeType.IMMORTAL, false);
    }

    private static void putPlayer(UUID id, CapeType cape, boolean worthy) {
        PLAYER_MAP.put(id, new Entry(id, cape, worthy));
    }

    public static void smite(LivingEntity entity) {
        var world = entity.getWorld();
        var random = entity.getRandom();
        var stack = entity.getStackInHand(entity.getActiveHand());

        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 60, 1), entity);
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 2), entity);

        entity.setStackInHand(entity.getActiveHand(), ItemStack.EMPTY);

        if (entity instanceof PlayerEntity) {
            ((PlayerEntity) entity).sendMessage(Text.translatable("You have no right!"), true);
            entity.dropStack(stack);
        }

        world.playSoundFromEntity(null, entity, IncubusSounds.DRIP, SoundCategory.PLAYERS, 2F, 2F);

        if (entity.getHealth() < 5F) {
            entity.damage(IncubusDamageSources.UNWORTHY, 10000F);
        }
        else {
            entity.damage(IncubusDamageSources.UNWORTHY, 0.1F);
            entity.setHealth(0.01F);
        }

        if (!world.isClient()) {
            Box bounds = entity.getBoundingBox(entity.getPose());
            for (int i = 0; i < Math.pow(bounds.getAverageSideLength() * 4, 2); i++) {
                ((ServerWorld) world).spawnParticles(ParticleTypes.SOUL_FIRE_FLAME, entity.getX() + (random.nextDouble() * bounds.getXLength() - bounds.getXLength() / 2), entity.getY() + (random.nextDouble() * bounds.getYLength()), entity.getZ() + (random.nextDouble() * bounds.getZLength() - bounds.getZLength() / 2), random.nextInt(4), 0, 0, 0, 0.9);
            }
        }
    }

    public record Entry(UUID playerId, CapeType capeType, boolean worthy) {}

    public static void init(){
        String[] args = FabricLoader.getInstance().getLaunchArguments(false);
        bypassWorthiness = Arrays.asList(args).contains("WORTHY");
        if (bypassWorthiness) {
            IncubusCore.LOG.info("Bypassed worthiness check.");
        }
    }

    public enum CapeType {
        IMMORTAL(locate("textures/capes/immortal.png"), true),
        LUNAR(locate("textures/capes/inaba_of_the_moon.png"), true),
        V1(locate("textures/capes/v1.png"), true),
        UNDERGROUND_ASTRONOMY(locate("textures/capes/underground_astronomy.png"), true),
        LUCKY_STARS(locate("textures/capes/lucky_stars.png"), true),
        PALE_ASTRONOMY(locate("textures/capes/pale_astronomy.png"), true),
        GUDY(locate("textures/capes/gudy.png"), true),
        CHROMED(locate("textures/capes/chromed.png"), true),
        LEAD(locate("textures/capes/leads.png"), true),
        NONE(null, false);

        public final Identifier capePath;
        public final boolean render;

        CapeType(Identifier capePath, boolean render) {
            this.capePath = capePath;
            this.render = render;
        }
    }

    static {
        putPlayer(UUID.fromString("f7957087-549e-4ca3-878e-48f36569dd3e"), CapeType.LUNAR, true); //azzy
        putPlayer(UUID.fromString("a250dea2-a0ec-4aa4-bfa9-858a44466241"), CapeType.V1, true); //pie
        putPlayer(UUID.fromString("32e3b46b-2d54-47c7-886e-8e53889592d6"), CapeType.LEAD, true); //kal
        putPlayer(UUID.fromString("935bdd48-be5a-4537-95e4-e2274b2a9792"), CapeType.LEAD, true); //jack
        putPlayer(UUID.fromString("904bc7cc-c99d-40c8-9297-2efc3e08205c"), CapeType.LEAD, true); //sun
        putPlayer(UUID.fromString("510d0e83-67ef-49c6-83b4-d83ed34efeee"), CapeType.GUDY, false); //gud
        putPlayer(UUID.fromString("9bab9ead-385d-421e-812f-b8cac440d183"), CapeType.IMMORTAL, true); //24
        putPlayer(UUID.fromString("5c868fb2-7727-4cb8-a7d6-3083fa175063"), CapeType.IMMORTAL, false); //cda
        putPlayer(UUID.fromString("5010ad09-0229-4d70-8a2c-bc254821dcb3"), CapeType.UNDERGROUND_ASTRONOMY, true); // daf
        putPlayer(UUID.fromString("6105cb83-5d33-4e45-8adb-f24ee0085bf5"), CapeType.LUCKY_STARS, false); // krak
        putPlayer(UUID.fromString("f962000a-ee12-40ea-abd5-e15f7492f039"), CapeType.PALE_ASTRONOMY, false); // dra
        putPlayer(UUID.fromString("f791d11d-5415-4c28-99e7-ac6a0b2fec28"), CapeType.PALE_ASTRONOMY, false); // opl
        putPlayer(UUID.fromString("a1732122-e22e-4edf-883c-09673eb55de8"), CapeType.PALE_ASTRONOMY, false); // maya
        putPlayer(UUID.fromString("5a4c901c-2477-436b-a5b3-3b753fad43a5")); //reo
        putPlayer(UUID.fromString("c31a8cfa-ecd7-4ec2-8976-cb86c8c651e2")); //prof
        putPlayer(UUID.fromString("004679d7-3163-4e06-a36f-8c6c531d7681")); //solly
        putPlayer(UUID.fromString("73c30c75-e6d7-4141-9c14-06019b6888c1")); //ash
    }
}
