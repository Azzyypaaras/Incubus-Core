package net.id.incubus_core.devel;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.id.incubus_core.IncubusCore;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A class that provides some useful tools for developers.
 * @author gudenau
 */
public final class Devel {
    private static final Set<Devel> DEVELS = new HashSet<>();
    private static final boolean isDevel = FabricLoader.getInstance().isDevelopmentEnvironment();
    private static Path directory = Path.of(IncubusDevel.Config.DIRECTORY);

    static final Set<String> BAD_FEATURES = new HashSet<>();
    @Environment(EnvType.CLIENT)
    static final Set<Identifier> MISSING_TEXTURES = new HashSet<>();
    @Environment(EnvType.CLIENT)
    static final Set<Identifier> BAD_TEXTURES = new HashSet<>();
    @Environment(EnvType.CLIENT)
    static final Set<String> MISSING_LANGUAGE_KEYS = new HashSet<>();

    private final String mod_id;

    /**
     * Creates a Devel tool for the given mod ID.
     */
    @SuppressWarnings("UnusedReturnValue")
    public static Devel createDevelFor(String mod_id) {
        return new Devel(mod_id);
    }

    Devel(String mod_id) {
        this.mod_id = mod_id;
        DEVELS.add(this);
    }

    static void init() {
        if (DEVELS.size() == 0) {
            IncubusCore.LOG.info("No devels loaded");
            return;
        }
        // Create devel directory if it doesn't exist
        if (!Files.isDirectory(directory)) {
            try {
                Files.createDirectory(directory);
            } catch (IOException e) {
                IncubusCore.LOG.error("Failed to create \"{}\" directory. Using default directory...", directory);
                // If something doesn't work, just plop the files in the /.minecraft/ directory.
                directory = Path.of("./");
                e.printStackTrace();
            }
        }
        // This whole string builder thing is just to make the logs look nice.
        StringBuilder mod_ids = new StringBuilder();
        for (var devel : DEVELS) {
            mod_ids.append(", ").append(devel.mod_id);
        }
        String list_of_mods = mod_ids.substring(2); // chop off the first comma
        IncubusCore.LOG.info("Devels loaded for: {}.", list_of_mods);
        // Save on shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(Devel::save));
    }

    @Environment(EnvType.CLIENT)
    static void clientInit(){
        // We don't need to do all the fancy stuff for this method, since
        // it should already be covered by the common init.
        Runtime.getRuntime().addShutdownHook(new Thread(Devel::clientSave));
    }

    /**
     * @return true if the mod is being run in a development environment.
     */
    public static boolean isDevel() {
        return isDevel;
    }

    private static void save(){
        for (var devel : DEVELS) {
            IncubusCore.LOG.info("Saving devel log for {}.", devel.mod_id);
            var logFile = directory.resolve(Path.of(devel.mod_id + "_todo_server.txt"));

            try (var writer = new UncheckedWriter(Files.newBufferedWriter(logFile, StandardCharsets.UTF_8))) {
                devel.dumpStrings(writer, "Bad features", BAD_FEATURES);
            } catch (UncheckedIOException | IOException e) {
                IncubusCore.LOG.error("Failed to write \"{}\" devel log for mod \"{}\".", logFile.toString(), devel.mod_id);
                e.printStackTrace();
            }
        }
    }

    @Environment(EnvType.CLIENT)
    private static void clientSave(){
        for (var devel : DEVELS) {
            IncubusCore.LOG.info("Saving client devel log for {}.", devel.mod_id);
            var logFile = directory.resolve(Path.of(devel.mod_id + "_todo_client.txt"));

            try (var writer = new UncheckedWriter(Files.newBufferedWriter(logFile, StandardCharsets.UTF_8))) {
                devel.dumpIds(writer, "Missing textures", MISSING_TEXTURES);
                devel.dumpIds(writer, "Textures with broken metadata", BAD_TEXTURES);
                devel.dumpStrings(writer, "Missing language keys", MISSING_LANGUAGE_KEYS);
            } catch (UncheckedIOException | IOException e) {
                IncubusCore.LOG.error("Failed to write \"{}\" client devel log for mod \"{}\".", logFile.toString(), devel.mod_id);
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    private void dumpIds(UncheckedWriter writer, String message, Collection<Identifier> ids){
        synchronized(ids){
            if(!ids.isEmpty()){
                writer.write(message + ":\n");
                ids.stream()
                        .filter(id -> id.getNamespace().equals(mod_id))
                        .sorted(Identifier::compareTo)
                        .forEachOrdered((id)->writer.write("    " + id + '\n'));
            }
        }
    }

    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    private void dumpStrings(UncheckedWriter writer, String message, Collection<String> strings){
        synchronized(strings){
            if(!strings.isEmpty()){
                writer.write(message + ":\n");
                strings.stream()
                        .filter(s -> s.contains(mod_id))
                        .sorted(String::compareTo)
                        .forEachOrdered((id)->writer.write("    " + id + '\n'));
            }
        }
    }
}
