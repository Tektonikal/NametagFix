package tektonikal.evil.nametagFix.client;

import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
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
            OrderedRenderCommandQueue queue,
            CameraRenderState cameraRenderState,
            Matrix4f positionMatrix,
            Matrix4f normalMatrix
    ) {}
}
