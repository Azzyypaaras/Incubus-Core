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
 * <br>~ Jack
 * @author gudenau
 */
@SuppressWarnings("unused")
public final class Devel {
    private static final String[] MOD_IDS = IncubusDevel.Config.MODS;
    private static final boolean isDevel = FabricLoader.getInstance().isDevelopmentEnvironment();
    private static Path directory = IncubusDevel.Config.DIRECTORY;

    static final Set<String> BAD_FEATURES = new HashSet<>();

    private Devel() {}

    static void init() {
        if (MOD_IDS.length == 0) {
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
        IncubusCore.LOG.info("Devels loaded for: {}.", String.join(", ", MOD_IDS));
        // Save on shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(Devel::save));
    }

    /**
     * @return true if the mod is being run in a development environment.
     */
    public static boolean isDevel() {
        return isDevel;
    }

    private static void save(){
        for (var mod_id : MOD_IDS) {
            IncubusCore.LOG.info("Saving devel log for {}.", mod_id);
            var logFile = directory.resolve(Path.of(mod_id + "_todo_server.txt"));

            try (var writer = new UncheckedWriter(Files.newBufferedWriter(logFile, StandardCharsets.UTF_8))) {
                dumpStrings(mod_id, writer, "Bad features", BAD_FEATURES);
            } catch (UncheckedIOException | IOException e) {
                IncubusCore.LOG.error("Failed to write \"{}\" devel log for mod \"{}\".", logFile.toString(), mod_id);
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    private static void dumpIds(String mod_id, UncheckedWriter writer, String message, Collection<Identifier> ids){
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
    private static void dumpStrings(String mod_id, UncheckedWriter writer, String message, Collection<String> strings){
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

    @Environment(EnvType.CLIENT)
    static final class ClientDevel {
        static final Set<Identifier> MISSING_TEXTURES = new HashSet<>();
        static final Set<Identifier> BAD_TEXTURES = new HashSet<>();
        static final Set<String> MISSING_LANGUAGE_KEYS = new HashSet<>();

        static void clientInit(){
            // We don't need to do all the fancy stuff for this method, since
            // it should already be covered by the common init.
            Runtime.getRuntime().addShutdownHook(new Thread(ClientDevel::clientSave));
        }

        private static void clientSave(){
            for (var mod_id : MOD_IDS) {
                IncubusCore.LOG.info("Saving client devel log for {}.", mod_id);
                var logFile = directory.resolve(Path.of(mod_id + "_todo_client.txt"));

                try (var writer = new UncheckedWriter(Files.newBufferedWriter(logFile, StandardCharsets.UTF_8))) {
                    dumpIds(mod_id, writer, "Missing textures", MISSING_TEXTURES);
                    dumpIds(mod_id, writer, "Textures with broken metadata", BAD_TEXTURES);
                    dumpStrings(mod_id, writer, "Missing language keys", MISSING_LANGUAGE_KEYS);
                } catch (UncheckedIOException | IOException e) {
                    IncubusCore.LOG.error("Failed to write \"{}\" client devel log for mod \"{}\".", logFile.toString(), mod_id);
                    e.printStackTrace();
                }
            }
        }
    }
}
