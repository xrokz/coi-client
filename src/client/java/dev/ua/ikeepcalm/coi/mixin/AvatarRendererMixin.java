package dev.ua.ikeepcalm.coi.mixin;

import dev.ua.ikeepcalm.coi.client.mcf.AvatarRenderStateAccessor;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public class AvatarRendererMixin {

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void coi$extractPlayerUuid(Avatar entity, AvatarRenderState state, float partialTicks, CallbackInfo ci) {
        ((AvatarRenderStateAccessor) state).coi$setPlayerUuid(entity.getUUID().toString());
    }

}
