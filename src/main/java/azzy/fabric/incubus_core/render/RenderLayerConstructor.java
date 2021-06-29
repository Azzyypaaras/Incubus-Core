package azzy.fabric.incubus_core.render;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexFormat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class RenderLayerConstructor extends RenderLayer {

    private static final Constructor<?> constructor;

    public RenderLayerConstructor(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }

    public static RenderLayer buildMultiPhase(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, MultiPhaseParameters phases) {
        try {
            return (RenderLayer) constructor.newInstance(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, phases);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    static {
        constructor = RenderLayer.getLines().getClass().getDeclaredConstructors()[0];
        constructor.setAccessible(true);
    }
}
