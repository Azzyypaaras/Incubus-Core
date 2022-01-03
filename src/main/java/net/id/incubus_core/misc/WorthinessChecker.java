package net.id.incubus_core.misc;

import net.fabricmc.loader.api.FabricLoader;
import net.id.incubus_core.IncubusCore;
import net.minecraft.util.Identifier;

import java.util.*;

import static net.id.incubus_core.IncubusCore.locate;

public class WorthinessChecker {
    public static boolean bypassWorthiness;

    private static final HashMap<UUID, Entry> PLAYER_MAP = new HashMap<>();

    public static boolean isPlayerWorthy(UUID uuid) {
        return Optional.ofNullable(PLAYER_MAP.get(uuid)).map(entry -> entry.worthy).orElse(bypassWorthiness);
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
        LUNAR(locate("textures/capes/lunarian.png"), "High incubus | ", true),
        BRAIN_ROT(locate("textures/capes/brain_rot.png"), "Lord of brain rot | ", true),
        V1(locate("textures/capes/v1.png"), "ULTRASHILL | ", true),
        GUDY(locate("textures/capes/gudy.png"), true),
        CHROMED(locate("textures/capes/chromed.png"), true),
        LEAD(locate("textures/capes/leads.png"), true),
        NONE(null, false);

        public final Identifier capePath;
        public final String prefix;
        public final boolean render;

        CapeType(Identifier capePath, String prefix, boolean render) {
            this.capePath = capePath;
            this.prefix = prefix;
            this.render = render;
        }

        CapeType(Identifier capePath, boolean render) {
            this(capePath, "", render);
        }
    }

    static {
        putPlayer(UUID.fromString("f7957087-549e-4ca3-878e-48f36569dd3e"), CapeType.LUNAR, true); //azzy
        putPlayer(UUID.fromString("a250dea2-a0ec-4aa4-bfa9-858a44466241"), CapeType.V1, true); //pie
        putPlayer(UUID.fromString("0461feb0-c0a6-4020-a612-3d24a4ff3f3b"), CapeType.BRAIN_ROT, true); //jer
        putPlayer(UUID.fromString("32e3b46b-2d54-47c7-886e-8e53889592d6"), CapeType.LEAD, true); //kal
        putPlayer(UUID.fromString("935bdd48-be5a-4537-95e4-e2274b2a9792"), CapeType.LEAD, true); //jack
        putPlayer(UUID.fromString("904bc7cc-c99d-40c8-9297-2efc3e08205c"), CapeType.LEAD, true); //sun
        putPlayer(UUID.fromString("510d0e83-67ef-49c6-83b4-d83ed34efeee"), CapeType.GUDY, false); //gud
        putPlayer(UUID.fromString("9bab9ead-385d-421e-812f-b8cac440d183"), CapeType.IMMORTAL, true); //24
        putPlayer(UUID.fromString("5c868fb2-7727-4cb8-a7d6-3083fa175063"), CapeType.IMMORTAL, false); //cda
        putPlayer(UUID.fromString("5a4c901c-2477-436b-a5b3-3b753fad43a5")); //reo
        putPlayer(UUID.fromString("c31a8cfa-ecd7-4ec2-8976-cb86c8c651e2")); //prof
        putPlayer(UUID.fromString("33776d36-c83c-4695-8299-2ef87deea062")); //plat
        putPlayer(UUID.fromString("004679d7-3163-4e06-a36f-8c6c531d7681")); //solly
        putPlayer(UUID.fromString("0aa0fe56-e9d9-4858-b6a6-a40a26fff680")); //sxf
        putPlayer(UUID.fromString("73c30c75-e6d7-4141-9c14-06019b6888c1")); //ash
    }
}
