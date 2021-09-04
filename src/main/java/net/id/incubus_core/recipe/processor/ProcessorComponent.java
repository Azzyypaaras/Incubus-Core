package net.id.incubus_core.recipe.processor;

import net.id.incubus_core.systems.Simulation;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.network.PacketByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface ProcessorComponent<C> {

    ProcessResult apply(Simulation simulation);

    boolean validate();

    default Optional<C> fromJson(JsonObject json, boolean required) {
        Optional<C> componentOptional = Optional.empty();
        try {
            componentOptional = Optional.ofNullable(readFromJson(json.get(getComponentName())));
        }
        catch (ClassCastException | NullPointerException ignored) {
        }
        catch (JsonSyntaxException e) {
            if(required)
                throw e;
        }
        return componentOptional;
    }

    @Nullable
    C readFromJson(JsonElement json);

    void writeToBuf(PacketByteBuf buf, C component);

    @NotNull
    C readFromBuf(PacketByteBuf buf);

    @NotNull
    String getComponentName();

    enum ProcessResult {
        VALID,
        INVALID,
        SUCCESS,
        ERROR;

        public static ProcessResult getAct(ProcessResult processResult) {
            return switch (processResult) {
                case VALID -> SUCCESS;
                case INVALID -> ERROR;
                default -> processResult;
            };
        }
    }
}
