package net.id.incubus_core.datagen;

import net.id.incubus_core.IncubusCoreCommon;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Metadata {

    public final String resourcePath;
    public final String dataPath;

    public final boolean allowRegen;

    public Metadata(String id, String regenVar) {
        genCheck: {
            String[] args = FabricLoader.getInstance().getLaunchArguments(false);
            for (String arg : args) {
                if(arg.equals(regenVar)) {
                    allowRegen = true;
                    break genCheck;
                }
            }
            allowRegen = false;
        }

        IncubusCoreCommon.LOG.error("Datagen integration detected for " + id + " - arg - " + regenVar + " - state - " + (allowRegen ? "ENABLED" : "DISABLED"));

        this.resourcePath = Paths.get(System.getProperty("user.dir")).getParent().toString() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "assets" + File.separator + id;
        this.dataPath = Paths.get(System.getProperty("user.dir")).getParent().toString() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "data" + File.separator + id;
    }

    public File genResourceJson(String name, String path, ResourceType type) {
        if(!path.isEmpty()) {
            path = path + File.separator;
        }
        File file = new File(resourcePath + File.separator + type.path + File.separator + path + name + ".json");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public File genResourceJson(String name, ResourceType type) {
        return genResourceJson(name, "", type);
    }

    public File genDataJson(String name, String path, DataType type) {
        if(!path.isEmpty()) {
            path = path + File.separator;
        }
        File file = new File(dataPath + File.separator + type.path + File.separator + path + name + ".json");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public File genDataJson(String name, DataType type) {
        return genDataJson(name, "", type);
    }

    enum ResourceType {
        BLOCKSTATE("blockstates"),
        ITEM_MODEL("models" + File.separator + "item"),
        BLOCK_MODEL("models" + File.separator + "block");

        ResourceType(String path) {
            this.path = path;
        }

        public final String path;
    }

    enum DataType {
        RECIPE("recipes"),
        BLOCK_LOOT("loot_tables" + File.separator + "blocks"),
        MOB_LOOT("loot_tables" + File.separator + "entities");

        DataType(String path) {
            this.path = path;
        }

        public final String path;
    }
}
