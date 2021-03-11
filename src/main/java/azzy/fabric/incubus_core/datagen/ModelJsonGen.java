package azzy.fabric.incubus_core.datagen;

import com.google.common.base.Charsets;
import net.minecraft.util.Identifier;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ModelJsonGen {

    public static void genItemJson(Metadata metadata, Identifier name) {
        String item = "{\n" +
                "  \"parent\": \"item/generated\",\n" +
                "  \"textures\": {\n" +
                "    \"layer0\": \"" + name.getNamespace() + ":item/" + name.getPath() + "\"\n" +
                "  }\n" +
                "}";

        try {
            FileUtils.writeStringToFile(metadata.genResourceJson(name.getPath(), "", Metadata.ResourceType.ITEM_MODEL), item, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void genBlockJson(Metadata metadata, Identifier texId, Identifier name, String path) {
        String block = "{\n" +
                "  \"parent\": \"minecraft:block/cube_all\",\n" +
                "  \"textures\": {\n" +
                "    \"all\": \"" + texId.toString() + "\"\n" +
                "  }\n" +
                "}";

        String item = "{\n" +
                "  \"parent\": \"" + (name.getNamespace() + ":block" + "/" + path + name.getPath()) + "\"\n" +
                "}";

        try {
            FileUtils.writeStringToFile(metadata.genResourceJson(name.getPath(), path, Metadata.ResourceType.BLOCK_MODEL), block, Charsets.UTF_8);
            FileUtils.writeStringToFile(metadata.genResourceJson(name.getPath(), "", Metadata.ResourceType.ITEM_MODEL), item, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void genSlabJsons(Metadata metadata, Identifier texId, Identifier name, String path) {
        String bottom = "{\n" +
                "  \"parent\": \"minecraft:block/slab\",\n" +
                "  \"textures\": {\n" +
                "    \"bottom\": \"" + texId.toString() + "\",\n" +
                "    \"top\": \"" + texId.toString() + "\",\n" +
                "    \"side\": \"" + texId.toString() + "\"\n" +
                "  }\n" +
                "}";

        String top = "{\n" +
                "  \"parent\": \"minecraft:block/slab_top\",\n" +
                "  \"textures\": {\n" +
                "    \"bottom\": \"" + texId.toString() + "\",\n" +
                "    \"top\": \"" + texId.toString() + "\",\n" +
                "    \"side\": \"" + texId.toString() + "\"\n" +
                "  }\n" +
                "}";

        String item = "{\n" +
                "  \"parent\": \"" + (name.getNamespace() + ":block" + "/" + path + name.getPath()) + "\"\n" +
                "}";

        try {
            FileUtils.writeStringToFile(metadata.genResourceJson(name.getPath(), path, Metadata.ResourceType.BLOCK_MODEL), bottom, Charsets.UTF_8);
            FileUtils.writeStringToFile(metadata.genResourceJson(name.getPath() + "_top", path, Metadata.ResourceType.BLOCK_MODEL), top, Charsets.UTF_8);
            FileUtils.writeStringToFile(metadata.genResourceJson(name.getPath(), "", Metadata.ResourceType.ITEM_MODEL), item, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void genStairJsons(Metadata metadata, Identifier texId, Identifier name, String path) {
        String normal = "{\n" +
                "  \"parent\": \"minecraft:block/stairs\",\n" +
                "  \"textures\": {\n" +
                "    \"bottom\": \"" + texId.toString() + "\",\n" +
                "    \"top\": \"" + texId.toString() + "\",\n" +
                "    \"side\": \"" + texId.toString() + "\"\n" +
                "  }\n" +
                "}";

        String inner = "{\n" +
                "  \"parent\": \"minecraft:block/inner_stairs\",\n" +
                "  \"textures\": {\n" +
                "    \"bottom\": \"" + texId.toString() + "\",\n" +
                "    \"top\": \"" + texId.toString() + "\",\n" +
                "    \"side\": \"" + texId.toString() + "\"\n" +
                "  }\n" +
                "}";

        String outer = "{\n" +
                "  \"parent\": \"minecraft:block/outer_stairs\",\n" +
                "  \"textures\": {\n" +
                "    \"bottom\": \"" + texId.toString() + "\",\n" +
                "    \"top\": \"" + texId.toString() + "\",\n" +
                "    \"side\": \"" + texId.toString() + "\"\n" +
                "  }\n" +
                "}";

        String item = "{\n" +
                "  \"parent\": \"" + (name.getNamespace() + ":block" + "/" + path + name.getPath()) + "\"\n" +
                "}";

        path = path.replace('/', File.separatorChar);

        try {
            FileUtils.writeStringToFile(metadata.genResourceJson(name.getPath(), path, Metadata.ResourceType.BLOCK_MODEL), normal, Charsets.UTF_8);
            FileUtils.writeStringToFile(metadata.genResourceJson(name.getPath() + "_inner", path, Metadata.ResourceType.BLOCK_MODEL), inner, Charsets.UTF_8);
            FileUtils.writeStringToFile(metadata.genResourceJson(name.getPath() + "_outer", path, Metadata.ResourceType.BLOCK_MODEL), outer, Charsets.UTF_8);
            FileUtils.writeStringToFile(metadata.genResourceJson(name.getPath(), "", Metadata.ResourceType.ITEM_MODEL), item, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void genWallJsons(Metadata metadata, Identifier texId, Identifier name, String path) {
        String post = "{\n" +
                "  \"parent\": \"minecraft:block/template_wall_post\",\n" +
                "  \"textures\": {\n" +
                "    \"wall\": \"" + texId.toString() + "\"\n" +
                "  }\n" +
                "}";

        String side = "{\n" +
                "  \"parent\": \"minecraft:block/template_wall_side\",\n" +
                "  \"textures\": {\n" +
                "    \"wall\": \"" + texId.toString() + "\"\n" +
                "  }\n" +
                "}";

        String tall = "{\n" +
                "  \"parent\": \"minecraft:block/template_wall_side_tall\",\n" +
                "  \"textures\": {\n" +
                "    \"wall\": \"" + texId.toString() + "\"\n" +
                "  }\n" +
                "}";

        String item = "{\n" +
                "  \"parent\": \"minecraft:block/wall_inventory\",\n" +
                "  \"textures\": {\n" +
                "    \"wall\": \"" + texId.toString() + "\"\n" +
                "  }\n" +
                "}";

        path = path.replace('/', File.separatorChar);

        try {
            FileUtils.writeStringToFile(metadata.genResourceJson(name.getPath() + "_post", path, Metadata.ResourceType.BLOCK_MODEL), post, Charsets.UTF_8);
            FileUtils.writeStringToFile(metadata.genResourceJson(name.getPath() + "_side", path, Metadata.ResourceType.BLOCK_MODEL), side, Charsets.UTF_8);
            FileUtils.writeStringToFile(metadata.genResourceJson(name.getPath() + "_side_tall", path, Metadata.ResourceType.BLOCK_MODEL), tall, Charsets.UTF_8);
            FileUtils.writeStringToFile(metadata.genResourceJson(name.getPath(), "", Metadata.ResourceType.ITEM_MODEL), item, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
