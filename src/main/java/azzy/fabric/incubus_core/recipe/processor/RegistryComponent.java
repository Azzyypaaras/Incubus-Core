package azzy.fabric.incubus_core.recipe.processor;

import com.google.gson.JsonElement;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class RegistryComponent<O> extends NamedComponent<O> {

    private final Registry<O> registry;
    private final O defaultValue;

    protected RegistryComponent(Registry<O> registry, O defaultValue, String componentName) {
        super(componentName);
        this.registry = registry;
        this.defaultValue = defaultValue;
    }

    @Override
    public @Nullable O readFromJson(JsonElement componentJson) {
        return Optional
                .ofNullable(registry.get(Identifier.tryParse(componentJson.getAsString())))
                .orElse(defaultValue);
    }

    @Override
    public void writeToBuf(PacketByteBuf buf, O component) {
        buf.writeIdentifier(registry.getId(component));
    }

    @Override
    public @NotNull O readFromBuf(PacketByteBuf buf) {
        return Optional
                .ofNullable(registry.get(buf.readIdentifier()))
                .orElse(defaultValue);
    }
}
