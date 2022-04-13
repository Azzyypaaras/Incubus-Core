package net.id.incubus_core.woodtypefactory.api.chest.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.id.incubus_core.util.IncubusHoliday;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

import java.util.Set;

import static net.minecraft.client.render.TexturedRenderLayers.CHEST_ATLAS_TEXTURE;

@Environment(EnvType.CLIENT)
public class IncubusChestTexture {
    public final SpriteIdentifier single;
    public final SpriteIdentifier left;
    public final SpriteIdentifier right;

    public IncubusChestTexture(String modId, String name){
        // return new SpriteIdentifier(CHEST_ATLAS_TEXTURE, new Identifier("entity/chest/" + variant));

        // For now we only support Christmas chests.
        var prefix = (switch (IncubusHoliday.get()){
            case CHRISTMAS -> IncubusHoliday.CHRISTMAS;
            default -> IncubusHoliday.NONE;
        }).getName();
        single = new SpriteIdentifier(CHEST_ATLAS_TEXTURE, new Identifier(modId, "entity/chest/" + name + '/' + prefix));
        left = new SpriteIdentifier(CHEST_ATLAS_TEXTURE, new Identifier(modId, "entity/chest/" + name + '/' + prefix + "_left"));
        right = new SpriteIdentifier(CHEST_ATLAS_TEXTURE, new Identifier(modId, "entity/chest/" + name + '/' + prefix + "_right"));
    }

    public Set<SpriteIdentifier> textures(){
        return Set.of(single, left, right);
    }

    public SpriteIdentifier single(){
        return single;
    }

    public SpriteIdentifier left(){
        return left;
    }

    public SpriteIdentifier right(){
        return right;
    }
}
