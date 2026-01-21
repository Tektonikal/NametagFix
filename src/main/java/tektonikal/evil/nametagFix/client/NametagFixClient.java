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
    public static List<DeferredLabel> deferredSeethroughLabels = new ArrayList<>();
    public static List<DeferredLabel> deferredNormalLabels = new ArrayList<>();

    public static boolean renderingDeferred = false;

    /**
     * Stores the final computed label data, mirroring OrderedRenderCommandQueueImpl.LabelCommand
     */
    public record DeferredLabel(
            Matrix4f matricesEntry,
            float x,
            int y,
            Text text,
            int lightCoords,
            int color,
            int backgroundColor,
            double distanceToCameraSq
    ) {}

    public static void clear() {
        deferredSeethroughLabels.clear();
        deferredNormalLabels.clear();
    }
}
