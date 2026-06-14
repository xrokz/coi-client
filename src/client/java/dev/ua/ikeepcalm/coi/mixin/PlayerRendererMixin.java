package dev.ua.ikeepcalm.coi.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.ua.ikeepcalm.coi.client.mcf.AvatarRenderStateAccessor;
import dev.ua.ikeepcalm.coi.client.mcf.MythicalFormManager;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class PlayerRendererMixin {

    @Inject(method = "submit", at = @At("HEAD"), cancellable = true)
    private void coi$renderMythicalForm(LivingEntityRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState, CallbackInfo ci) {
        if (state instanceof AvatarRenderState avatarState) {
            String playerUuid = ((AvatarRenderStateAccessor) avatarState).coi$getPlayerUuid();
            if (playerUuid != null) {
                String form = MythicalFormManager.getForm(playerUuid);
                if (form != null) {
                    poseStack.pushPose();
                    poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(180.0F - state.bodyRot));

                    collector.order(0).submitCustomGeometry(
                            poseStack,
                            RenderTypes.entityTranslucent(Identifier.fromNamespaceAndPath("coi-client", "textures/entity/white.png")),
                            (pose, consumer) -> MythicalFormManager.renderFormSubmit(form, avatarState, pose, consumer)
                    );

                    poseStack.popPose();

                    // Preserve the name tag display via accessor invoker
                    ((EntityRendererAccessor) this).callSubmitNameDisplay(avatarState, poseStack, collector, cameraState);

                    ci.cancel();
                }
            }
        }
    }
}
