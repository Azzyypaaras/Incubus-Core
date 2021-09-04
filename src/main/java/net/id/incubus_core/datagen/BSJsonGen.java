package net.id.incubus_core.datagen;

import com.google.common.base.Charsets;
import net.minecraft.util.Identifier;
import org.apache.commons.io.FileUtils;

import java.io.IOException;

@SuppressWarnings("unused")
public class BSJsonGen {

    public static void genBlockBS(Metadata metadata, Identifier identifier, String path) {

        if(!metadata.allowRegen)
            return;

        String id = identifier.getNamespace() + ":" + path + identifier.getPath();

        String json = "{\n" +
                "  \"variants\": {\n" +
                "    \"\": {\n" +
                "      \"model\": \"" + id + "\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        try {
            FileUtils.writeStringToFile(metadata.genResourceJson(identifier.getPath(), Metadata.ResourceType.BLOCKSTATE), json, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void genSlabBS(Metadata metadata, Identifier identifier, Identifier parentIdentifier, String path) {

        if(!metadata.allowRegen)
            return;

        String id = identifier.getNamespace() + ":" + path + identifier.getPath();
        String parentId = parentIdentifier.getNamespace() + ":" + path + parentIdentifier.getPath();

        String json = "{\n" +
                "  \"variants\": {\n" +
                "    \"type=bottom\": {\n" +
                "      \"model\": \"" + id + "\"\n" +
                "    },\n" +
                "    \"type=double\": {\n" +
                "      \"model\": \"" + parentId + "\"\n" +
                "    },\n" +
                "    \"type=top\": {\n" +
                "      \"model\": \"" + id + "_top\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        try {
            FileUtils.writeStringToFile(metadata.genResourceJson(identifier.getPath(), Metadata.ResourceType.BLOCKSTATE), json, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void genWallBS(Metadata metadata, Identifier identifier, String path) {

        if(!metadata.allowRegen)
            return;

        String id = identifier.getNamespace() + ":" + path + identifier.getPath();

        String json = "{\n" +
                "  \"multipart\": [\n" +
                "    {\n" +
                "      \"when\": {\n" +
                "        \"up\": \"true\"\n" +
                "      },\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"" + id + "_post\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"when\": {\n" +
                "        \"north\": \"low\"\n" +
                "      },\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"" + id + "_side\",\n" +
                "        \"uvlock\": true\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"when\": {\n" +
                "        \"east\": \"low\"\n" +
                "      },\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"" + id + "_side\",\n" +
                "        \"y\": 90,\n" +
                "        \"uvlock\": true\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"when\": {\n" +
                "        \"south\": \"low\"\n" +
                "      },\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"" + id + "_side\",\n" +
                "        \"y\": 180,\n" +
                "        \"uvlock\": true\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"when\": {\n" +
                "        \"west\": \"low\"\n" +
                "      },\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"" + id + "_side\",\n" +
                "        \"y\": 270,\n" +
                "        \"uvlock\": true\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"when\": {\n" +
                "        \"north\": \"tall\"\n" +
                "      },\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"" + id + "_side_tall\",\n" +
                "        \"uvlock\": true\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"when\": {\n" +
                "        \"east\": \"tall\"\n" +
                "      },\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"" + id + "_side_tall\",\n" +
                "        \"y\": 90,\n" +
                "        \"uvlock\": true\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"when\": {\n" +
                "        \"south\": \"tall\"\n" +
                "      },\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"" + id + "_side_tall\",\n" +
                "        \"y\": 180,\n" +
                "        \"uvlock\": true\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"when\": {\n" +
                "        \"west\": \"tall\"\n" +
                "      },\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"" + id + "_side_tall\",\n" +
                "        \"y\": 270,\n" +
                "        \"uvlock\": true\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        try {
            FileUtils.writeStringToFile(metadata.genResourceJson(identifier.getPath(), Metadata.ResourceType.BLOCKSTATE), json, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void genStairsBS(Metadata metadata, Identifier identifier, String path) {

        if(!metadata.allowRegen)
            return;

        String id = identifier.getNamespace() + ":" + path + identifier.getPath();

        String json = "{\n" +
                "  \"variants\": {\n" +
                "    \"facing=east,half=bottom,shape=inner_left\": {\n" +
                "      \"model\": \"" + id + "_inner\",\n" +
                "      \"y\": 270,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=east,half=bottom,shape=inner_right\": {\n" +
                "      \"model\": \"" + id + "_inner\"\n" +
                "    },\n" +
                "    \"facing=east,half=bottom,shape=outer_left\": {\n" +
                "      \"model\": \"" + id + "_outer\",\n" +
                "      \"y\": 270,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=east,half=bottom,shape=outer_right\": {\n" +
                "      \"model\": \"" + id + "_outer\"\n" +
                "    },\n" +
                "    \"facing=east,half=bottom,shape=straight\": {\n" +
                "      \"model\": \"" + id + "\"\n" +
                "    },\n" +
                "    \"facing=east,half=top,shape=inner_left\": {\n" +
                "      \"model\": \"" + id + "_inner\",\n" +
                "      \"x\": 180,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=east,half=top,shape=inner_right\": {\n" +
                "      \"model\": \"" + id + "_inner\",\n" +
                "      \"x\": 180,\n" +
                "      \"y\": 90,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=east,half=top,shape=outer_left\": {\n" +
                "      \"model\": \"" + id + "_outer\",\n" +
                "      \"x\": 180,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=east,half=top,shape=outer_right\": {\n" +
                "      \"model\": \"" + id + "_outer\",\n" +
                "      \"x\": 180,\n" +
                "      \"y\": 90,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=east,half=top,shape=straight\": {\n" +
                "      \"model\": \"" + id + "\",\n" +
                "      \"x\": 180,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=north,half=bottom,shape=inner_left\": {\n" +
                "      \"model\": \"" + id + "_inner\",\n" +
                "      \"y\": 180,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=north,half=bottom,shape=inner_right\": {\n" +
                "      \"model\": \"" + id + "_inner\",\n" +
                "      \"y\": 270,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=north,half=bottom,shape=outer_left\": {\n" +
                "      \"model\": \"" + id + "_outer\",\n" +
                "      \"y\": 180,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=north,half=bottom,shape=outer_right\": {\n" +
                "      \"model\": \"" + id + "_outer\",\n" +
                "      \"y\": 270,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=north,half=bottom,shape=straight\": {\n" +
                "      \"model\": \"" + id + "\",\n" +
                "      \"y\": 270,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=north,half=top,shape=inner_left\": {\n" +
                "      \"model\": \"" + id + "_inner\",\n" +
                "      \"x\": 180,\n" +
                "      \"y\": 270,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=north,half=top,shape=inner_right\": {\n" +
                "      \"model\": \"" + id + "_inner\",\n" +
                "      \"x\": 180,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=north,half=top,shape=outer_left\": {\n" +
                "      \"model\": \"" + id + "_outer\",\n" +
                "      \"x\": 180,\n" +
                "      \"y\": 270,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=north,half=top,shape=outer_right\": {\n" +
                "      \"model\": \"" + id + "_outer\",\n" +
                "      \"x\": 180,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=north,half=top,shape=straight\": {\n" +
                "      \"model\": \"" + id + "\",\n" +
                "      \"x\": 180,\n" +
                "      \"y\": 270,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=south,half=bottom,shape=inner_left\": {\n" +
                "      \"model\": \"" + id + "_inner\"\n" +
                "    },\n" +
                "    \"facing=south,half=bottom,shape=inner_right\": {\n" +
                "      \"model\": \"" + id + "_inner\",\n" +
                "      \"y\": 90,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=south,half=bottom,shape=outer_left\": {\n" +
                "      \"model\": \"" + id + "_outer\"\n" +
                "    },\n" +
                "    \"facing=south,half=bottom,shape=outer_right\": {\n" +
                "      \"model\": \"" + id + "_outer\",\n" +
                "      \"y\": 90,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=south,half=bottom,shape=straight\": {\n" +
                "      \"model\": \"" + id + "\",\n" +
                "      \"y\": 90,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=south,half=top,shape=inner_left\": {\n" +
                "      \"model\": \"" + id + "_inner\",\n" +
                "      \"x\": 180,\n" +
                "      \"y\": 90,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=south,half=top,shape=inner_right\": {\n" +
                "      \"model\": \"" + id + "_inner\",\n" +
                "      \"x\": 180,\n" +
                "      \"y\": 180,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=south,half=top,shape=outer_left\": {\n" +
                "      \"model\": \"" + id + "_outer\",\n" +
                "      \"x\": 180,\n" +
                "      \"y\": 90,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=south,half=top,shape=outer_right\": {\n" +
                "      \"model\": \"" + id + "_outer\",\n" +
                "      \"x\": 180,\n" +
                "      \"y\": 180,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=south,half=top,shape=straight\": {\n" +
                "      \"model\": \"" + id + "\",\n" +
                "      \"x\": 180,\n" +
                "      \"y\": 90,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=west,half=bottom,shape=inner_left\": {\n" +
                "      \"model\": \"" + id + "_inner\",\n" +
                "      \"y\": 90,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=west,half=bottom,shape=inner_right\": {\n" +
                "      \"model\": \"" + id + "_inner\",\n" +
                "      \"y\": 180,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=west,half=bottom,shape=outer_left\": {\n" +
                "      \"model\": \"" + id + "_outer\",\n" +
                "      \"y\": 90,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=west,half=bottom,shape=outer_right\": {\n" +
                "      \"model\": \"" + id + "_outer\",\n" +
                "      \"y\": 180,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=west,half=bottom,shape=straight\": {\n" +
                "      \"model\": \"" + id + "\",\n" +
                "      \"y\": 180,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=west,half=top,shape=inner_left\": {\n" +
                "      \"model\": \"" + id + "_inner\",\n" +
                "      \"x\": 180,\n" +
                "      \"y\": 180,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=west,half=top,shape=inner_right\": {\n" +
                "      \"model\": \"" + id + "_inner\",\n" +
                "      \"x\": 180,\n" +
                "      \"y\": 270,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=west,half=top,shape=outer_left\": {\n" +
                "      \"model\": \"" + id + "_outer\",\n" +
                "      \"x\": 180,\n" +
                "      \"y\": 180,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=west,half=top,shape=outer_right\": {\n" +
                "      \"model\": \"" + id + "_outer\",\n" +
                "      \"x\": 180,\n" +
                "      \"y\": 270,\n" +
                "      \"uvlock\": true\n" +
                "    },\n" +
                "    \"facing=west,half=top,shape=straight\": {\n" +
                "      \"model\": \"" + id + "\",\n" +
                "      \"x\": 180,\n" +
                "      \"y\": 180,\n" +
                "      \"uvlock\": true\n" +
                "    }\n" +
                "  }\n" +
                "}";
        try {
            FileUtils.writeStringToFile(metadata.genResourceJson(identifier.getPath(), Metadata.ResourceType.BLOCKSTATE), json, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
