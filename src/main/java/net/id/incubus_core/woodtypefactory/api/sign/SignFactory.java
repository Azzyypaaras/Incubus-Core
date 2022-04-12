package net.id.incubus_core.woodtypefactory.api.sign;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.mixin.lookup.BlockEntityTypeAccessor;
import net.id.incubus_core.mixin.woodtypefactory.sign.SignTypeAccessor;
import net.id.incubus_core.woodtypefactory.access.IncubusSignType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;

/**
 * Tell me your sign's secrets and I'll make it worth your while... by registering everything.
 */
public class SignFactory {
    private BlockEntityType<SignBlockEntity> blockEntityType;
    public final SignBlock signBlock;
    public final WallSignBlock wallSignBlock;
    public final String modId;
    public final SignType signType;

    public SignFactory(String modId, String signName, AbstractBlock.Settings signSettings, AbstractBlock.Settings wallSignSettings) {
        this.modId = modId;
        this.signType = SignTypeAccessor.callRegister(new IncubusSignType(this.modId + "_" + signName));

        this.signBlock = new SignBlock(signSettings, signType);
        this.wallSignBlock = new WallSignBlock(wallSignSettings.dropsLike(signBlock), signType);

        ((BlockEntityTypeAccessor) BlockEntityType.SIGN).getBlocks().add(this.signBlock);
        ((BlockEntityTypeAccessor) BlockEntityType.SIGN).getBlocks().add(this.wallSignBlock);
    }

    @Environment(EnvType.CLIENT)
    public void registerSignRenderers() {
        TexturedRenderLayers.WOOD_TYPE_TEXTURES.put(
                this.signType, new SpriteIdentifier(
                        TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, new Identifier("entity/signs/" + this.signType.getName())
                )
        );
    }
}
