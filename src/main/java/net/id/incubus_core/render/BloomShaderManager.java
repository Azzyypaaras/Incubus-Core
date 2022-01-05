package net.id.incubus_core.render;

import com.google.gson.JsonSyntaxException;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.id.incubus_core.IncubusCore;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class BloomShaderManager implements IdentifiableResourceReloadListener {
    public static final BloomShaderManager INSTANCE = new BloomShaderManager();
    private static final Identifier ID = new Identifier(IncubusCore.MODID, "bloom_shader_reloader");
    private static final Logger LOGGER = LogManager.getLogger();
    private static boolean initialized;
    private ShaderEffect effect;
    private Framebuffer framebuffer;

    @Override
    public Identifier getFabricId() {
        return ID;
    }

    @Override
    public CompletableFuture<Void> reload(Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
        return CompletableFuture.runAsync(() -> {
            if (effect != null) {
                effect.close();
            }
            var client = MinecraftClient.getInstance();
            var id = new Identifier(IncubusCore.MODID, "shaders/post/bloom.json");
            try {
                effect = new ShaderEffect(client.getTextureManager(), client.getResourceManager(), client.getFramebuffer(), id);
                effect.setupDimensions(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
                framebuffer = effect.getSecondaryTarget("light_sources");
            }
            catch (IOException iOException) {
                LOGGER.warn("Failed to load shader: " + id, iOException);
                effect = null;
                framebuffer = null;
            }
            catch (JsonSyntaxException iOException) {
                LOGGER.warn("Failed to parse shader: " + id, iOException);
                effect = null;
                framebuffer = null;
            }
        });
    }

    public ShaderEffect getEffect() {
        return effect;
    }

    public Framebuffer getFramebuffer() {
        return framebuffer;
    }

    private static void init() {
        synchronized (INSTANCE) {
            if (!initialized) {
                ((ReloadableResourceManager) MinecraftClient.getInstance().getResourceManager()).registerReloader(INSTANCE);
                initialized = true;
            }
        }
    }
}
