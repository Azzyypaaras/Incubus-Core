package azzy.fabric.incubus_core.misc;

import net.fabricmc.loader.api.FabricLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerChecker {

    public static final List<UUID> WORTHY_PLAYERS = new ArrayList<>();
    public static int AZZY = 0;
    public static int PIE = 1;
    public static int JER = 2;

    public static boolean isPlayerWorthy(UUID uuid) {
        if(FabricLoader.getInstance().isDevelopmentEnvironment())
            return true;

        for (int i = 0; i < WORTHY_PLAYERS.size(); i++) {
            if(i > 2) {
                return false;
            }
            if(uuid.equals(WORTHY_PLAYERS.get(i)))
                return true;
        }
        return false;
    }

    static {
        WORTHY_PLAYERS.add(UUID.fromString("f7957087-549e-4ca3-878e-48f36569dd3e"));
        WORTHY_PLAYERS.add(UUID.fromString("a250dea2-a0ec-4aa4-bfa9-858a44466241"));
        WORTHY_PLAYERS.add(UUID.fromString("0461feb0-c0a6-4020-a612-3d24a4ff3f3b"));
    }
}
