package azzy.fabric.incubus_core.datagen;

import com.google.common.base.Charsets;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public class RecipeJsonGen {

    public static final String PATTERN_PATH = "pattern";

    public static final String SMELTING_TEMPLATE = "{\n" +
            "  \"type\": \"minecraft:smelting\",\n" +
            "  \"ingredient\": {\n" +
            "    \"item\": \"component_0\"\n" +
            "  },\n" +
            "  \"result\": \"component_1\",\n" +
            "  \"experience\": component_2,\n" +
            "  \"cookingtime\": component_3\n" +
            "}";

    public static void genFurnaceRecipe(Metadata metadata, String name, Item input, Item output, float xp, int time) {

        if(!metadata.allowRegen)
            return;

        genDynamicRecipe(
                metadata,
                name,
                "smelting",
                SMELTING_TEMPLATE,
                new String[]{ Registry.ITEM.getId(input).toString(), Registry.ITEM.getId(output).toString(), String.valueOf(xp), String.valueOf(time)}
        );
    }

    public static void genDynamicRecipe(Metadata metadata, String name, String category, String template, String[] components) {

        if(!metadata.allowRegen)
            return;

        String output = "" + template;
        for (int i = 0; i < components.length; i++) {
            output = output.replace("component_" + i, components[i]);
        }
        try {
            FileUtils.writeStringToFile(metadata.genDataJson(name, PATTERN_PATH + File.separator + category, Metadata.DataType.RECIPE), output, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void gen2x2Recipe(Metadata metadata, String name, Item in, Item out, int count) {

        if(!metadata.allowRegen)
            return;

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

        try {
            FileUtils.writeStringToFile(metadata.genDataJson(name, PATTERN_PATH, Metadata.DataType.RECIPE), json, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void gen3x3Recipe(Metadata metadata, String name, Item in, Item out, int count) {

        if(!metadata.allowRegen)
            return;

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

        if(!metadata.allowRegen)
            return;

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

        if(!metadata.allowRegen)
            return;

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

        if(!metadata.allowRegen)
            return;

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
