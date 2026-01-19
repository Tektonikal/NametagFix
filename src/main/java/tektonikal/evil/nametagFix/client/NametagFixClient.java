package tektonikal.evil.nametagFix.client;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.text.Text;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class NametagFixClient {
    public static List<DeferredLabel> deferredLabels = new ArrayList<>();
    public static boolean isItTheLastWorldRenderStage = false;

    public record DeferredLabel(
            EntityRenderer<?, ?> renderer,
            EntityRenderState state,
            Text text,
            int light,
            Matrix4f positionMatrix,
            Matrix4f normalMatrix
    ) {}
}
