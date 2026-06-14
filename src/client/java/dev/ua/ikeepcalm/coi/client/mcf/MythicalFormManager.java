package dev.ua.ikeepcalm.coi.client.mcf;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.ua.ikeepcalm.coi.client.mcf.forms.*;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MythicalFormManager {

    private static final Map<String, String> uuidForms = new ConcurrentHashMap<>();
    private static final Map<String, MythicalCreatureForm> REGISTERED_FORMS = new ConcurrentHashMap<>();

    static {
        register(new FoolForm());
        register(new DoorForm());
        register(new ErrorForm());
        register(new TowerForm());
        register(new VisionaryForm());
        register(new SunForm());
        register(new TyrantForm());
        register(new HangedForm());
        register(new DarknessForm());
        register(new DeathForm());
        register(new GiantForm());
        register(new PriestForm());
        register(new DemonessForm());
        register(new ParagonForm());
        register(new HermitForm());
        register(new FortuneForm());
        register(new ChainedForm());
        register(new AbyssForm());
        register(new JusticiarForm());
        register(new EmperorForm());
    }

    public static void register(MythicalCreatureForm form) {
        String name = form.getPathwayName().toLowerCase();
        REGISTERED_FORMS.put(name, form);
        
        // Also register normalized variants for robustness
        String spaceRemoved = name.replace(" ", "");
        String underscoreRemoved = name.replace("_", "");
        String spaceToUnderscore = name.replace(" ", "_");
        String underscoreToSpace = name.replace("_", " ");
        
        REGISTERED_FORMS.put(spaceRemoved, form);
        REGISTERED_FORMS.put(underscoreRemoved, form);
        REGISTERED_FORMS.put(spaceToUnderscore, form);
        REGISTERED_FORMS.put(underscoreToSpace, form);
    }

    public static void handlePacket(String targetUuid, String params) {
        if (params == null || params.isBlank()) {
            uuidForms.remove(targetUuid);
            return;
        }
        String[] parts = params.split(":");
        if (parts.length < 3) {
            uuidForms.remove(targetUuid);
            return;
        }
        String pathwayName = parts[0].toLowerCase();
        String action = parts[2].toLowerCase();
        if ("stop".equals(action)) {
            uuidForms.remove(targetUuid);
        } else {
            uuidForms.put(targetUuid, pathwayName);
        }
    }

    public static boolean isTransformed(AbstractClientPlayer player) {
        if (player == null) return false;
        return uuidForms.containsKey(player.getUUID().toString());
    }

    public static String getForm(String playerUuid) {
        return uuidForms.get(playerUuid);
    }

    public static java.util.List<String> getRegisteredPathwayNames() {
        return REGISTERED_FORMS.values().stream()
                .map(MythicalCreatureForm::getPathwayName)
                .distinct()
                .sorted()
                .toList();
    }

    public static void clearAll() {
        uuidForms.clear();
    }

    public static void renderForm(String pathway, AbstractClientPlayer player, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
    }

    public static void renderFormSubmit(String pathway, AvatarRenderState state, PoseStack.Pose pose, VertexConsumer consumer) {
        MythicalCreatureForm form = REGISTERED_FORMS.get(pathway.toLowerCase());
        if (form != null) {
            form.render(state, pose, consumer);
        }
    }
}
