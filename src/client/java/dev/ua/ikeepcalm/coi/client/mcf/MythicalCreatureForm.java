package dev.ua.ikeepcalm.coi.client.mcf;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;

public interface MythicalCreatureForm {

    String getPathwayName();

    void render(AvatarRenderState state, PoseStack.Pose pose, VertexConsumer consumer);

    default void drawBox(PoseStack.Pose entry, VertexConsumer consumer, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float r, float g, float b, float a, int light) {
        // Front face
        addVertex(entry, consumer, minX, minY, maxZ, r, g, b, a, 0, 0, 0, 0, 1, light);
        addVertex(entry, consumer, maxX, minY, maxZ, r, g, b, a, 1, 0, 0, 0, 1, light);
        addVertex(entry, consumer, maxX, maxY, maxZ, r, g, b, a, 1, 1, 0, 0, 1, light);
        addVertex(entry, consumer, minX, maxY, maxZ, r, g, b, a, 0, 1, 0, 0, 1, light);

        // Back face
        addVertex(entry, consumer, minX, minY, minZ, r, g, b, a, 0, 0, 0, 0, -1, light);
        addVertex(entry, consumer, minX, maxY, minZ, r, g, b, a, 0, 1, 0, 0, -1, light);
        addVertex(entry, consumer, maxX, maxY, minZ, r, g, b, a, 1, 1, 0, 0, -1, light);
        addVertex(entry, consumer, maxX, minY, minZ, r, g, b, a, 1, 0, 0, 0, -1, light);

        // Top face
        addVertex(entry, consumer, minX, maxY, minZ, r, g, b, a, 0, 0, 0, 1, 0, light);
        addVertex(entry, consumer, minX, maxY, maxZ, r, g, b, a, 0, 1, 0, 1, 0, light);
        addVertex(entry, consumer, maxX, maxY, maxZ, r, g, b, a, 1, 1, 0, 1, 0, light);
        addVertex(entry, consumer, maxX, maxY, minZ, r, g, b, a, 1, 0, 0, 1, 0, light);

        // Bottom face
        addVertex(entry, consumer, minX, minY, minZ, r, g, b, a, 0, 0, 0, -1, 0, light);
        addVertex(entry, consumer, maxX, minY, minZ, r, g, b, a, 1, 0, 0, -1, 0, light);
        addVertex(entry, consumer, maxX, minY, maxZ, r, g, b, a, 1, 1, 0, -1, 0, light);
        addVertex(entry, consumer, minX, minY, maxZ, r, g, b, a, 0, 1, 0, -1, 0, light);

        // Right face
        addVertex(entry, consumer, maxX, minY, minZ, r, g, b, a, 0, 0, 1, 0, 0, light);
        addVertex(entry, consumer, maxX, maxY, minZ, r, g, b, a, 0, 1, 1, 0, 0, light);
        addVertex(entry, consumer, maxX, maxY, maxZ, r, g, b, a, 1, 1, 1, 0, 0, light);
        addVertex(entry, consumer, maxX, minY, maxZ, r, g, b, a, 1, 0, 1, 0, 0, light);

        // Left face
        addVertex(entry, consumer, minX, minY, minZ, r, g, b, a, 0, 0, -1, 0, 0, light);
        addVertex(entry, consumer, minX, minY, maxZ, r, g, b, a, 1, 0, -1, 0, 0, light);
        addVertex(entry, consumer, minX, maxY, maxZ, r, g, b, a, 1, 1, -1, 0, 0, light);
        addVertex(entry, consumer, minX, maxY, minZ, r, g, b, a, 0, 1, -1, 0, 0, light);
    }

    default void addVertex(PoseStack.Pose entry, VertexConsumer consumer, float x, float y, float z, float r, float g, float b, float a, float u, float v, float nx, float ny, float nz, int light) {
        consumer.addVertex(entry, x, y, z)
                .setColor(r, g, b, a)
                .setUv(u, v)
                .setOverlay(net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY)
                .setLight(light)
                .setNormal(entry, nx, ny, nz);
    }

}
