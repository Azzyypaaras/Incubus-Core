package azzy.fabric.incubus_core.datagen;

import com.google.common.base.Charsets;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import org.apache.commons.io.FileUtils;

import java.io.IOException;

public class RecipeJsonGen {

    public static final String PATTERN_PATH = "pattern";

    public static void gen2x2Recipe(Metadata metadata, String name, Item in, Item out, int count) {

        String output = Registry.ITEM.getId(out).toString();
        String input = Registry.ITEM.getId(in).toString();

        String json = "{\n" +
                "  \"type\": \"minecraft:crafting_shaped\",\n" +
                "  \"pattern\": [\n" +
                "    \"##\",\n" +
                "    \"##\"\n" +
                "  ],\n" +
                "  \"key\": {\n" +
                "    \"#\": {\n" +
                "      \"item\": \"" + input + "\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"result\": {\n" +
                "    \"item\": \"" + output + "\",\n" +
                "    \"count\": " + count + "\n" +
                "  }\n" +
                "}";

        //"{\n" +
        //        "  \"type\": \"minecraft:crafting_shaped\",\n" +
        //        "  \"pattern\": [\n" +
        //        "    \"###\",\n" +
        //        "    \"# #\",\n" +
        //        "    \"###\"\n" +
        //        "  ],\n" +
        //        "  \"key\": {\n" +
        //        "    \"#\": {\n" +
        //        "      \"tag\": \"minecraft:planks\"\n" +
        //        "    }\n" +
        //        "  },\n" +
        //        "  \"result\": {\n" +
        //        "    \"item\": \"minecraft:chest\"\n" +
        //        "  }\n" +
        //        "}"

        try {
            FileUtils.writeStringToFile(metadata.genDataJson(name, PATTERN_PATH, Metadata.DataType.RECIPE), json, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void gen3x3Recipe(Metadata metadata, String name, Item in, Item out, int count) {

        String output = Registry.ITEM.getId(out).toString();
        String input = Registry.ITEM.getId(in).toString();

        String json = "{\n" +
                "  \"type\": \"minecraft:crafting_shaped\",\n" +
                "  \"pattern\": [\n" +
                "    \"###\",\n" +
                "    \"###\",\n" +
                "    \"###\"\n" +
                "  ],\n" +
                "  \"key\": {\n" +
                "    \"#\": {\n" +
                "      \"item\": \"" + input + "\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"result\": {\n" +
                "    \"item\": \"" + output + "\",\n" +
                "    \"count\": " + count + "\n" +
                "  }\n" +
                "}";

        try {
            FileUtils.writeStringToFile(metadata.genDataJson(name, PATTERN_PATH, Metadata.DataType.RECIPE), json, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void genSlabRecipe(Metadata metadata, String name, Item in, Item out, int count) {

        String output = Registry.ITEM.getId(out).toString();
        String input = Registry.ITEM.getId(in).toString();

        String json = "{\n" +
                "  \"type\": \"minecraft:crafting_shaped\",\n" +
                "  \"pattern\": [\n" +
                "    \"###\"\n" +
                "  ],\n" +
                "  \"key\": {\n" +
                "    \"#\": {\n" +
                "      \"item\": \"" + input + "\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"result\": {\n" +
                "    \"item\": \"" + output + "\",\n" +
                "    \"count\": " + count + "\n" +
                "  }\n" +
                "}";

        try {
            FileUtils.writeStringToFile(metadata.genDataJson(name, PATTERN_PATH, Metadata.DataType.RECIPE), json, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void genStairsRecipe(Metadata metadata, String name, Item in, Item out, int count) {

        String output = Registry.ITEM.getId(out).toString();
        String input = Registry.ITEM.getId(in).toString();

        String json = "{\n" +
                "  \"type\": \"minecraft:crafting_shaped\",\n" +
                "  \"pattern\": [\n" +
                "    \"#  \",\n" +
                "    \"## \",\n" +
                "    \"###\"\n" +
                "  ],\n" +
                "  \"key\": {\n" +
                "    \"#\": {\n" +
                "      \"item\": \"" + input + "\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"result\": {\n" +
                "    \"item\": \"" + output + "\",\n" +
                "    \"count\": " + count + "\n" +
                "  }\n" +
                "}";

        try {
            FileUtils.writeStringToFile(metadata.genDataJson(name, PATTERN_PATH, Metadata.DataType.RECIPE), json, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void genWallRecipe(Metadata metadata, String name, Item in, Item out, int count) {

        String output = Registry.ITEM.getId(out).toString();
        String input = Registry.ITEM.getId(in).toString();

        String json = "{\n" +
                "  \"type\": \"minecraft:crafting_shaped\",\n" +
                "  \"pattern\": [\n" +
                "    \"###\",\n" +
                "    \"###\"\n" +
                "  ],\n" +
                "  \"key\": {\n" +
                "    \"#\": {\n" +
                "      \"item\": \"" + input + "\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"result\": {\n" +
                "    \"item\": \"" + output + "\",\n" +
                "    \"count\": " + count + "\n" +
                "  }\n" +
                "}";

        try {
            FileUtils.writeStringToFile(metadata.genDataJson(name, PATTERN_PATH, Metadata.DataType.RECIPE), json, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
