package tektonikal.evil.nametagFix.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.render.entity.EntityRenderer;
import tektonikal.evil.nametagFix.client.NametagFixClient;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @WrapMethod(method = "renderLabelIfPresent")
    private void yeah(EntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraRenderState, Operation<Void> original) {
        if (!NametagFixClient.isItTheLastWorldRenderStage) {
            Matrix4f positionMatrix = new Matrix4f(matrices.peek().getPositionMatrix());
            Matrix4f normalMatrix = new Matrix4f(matrices.peek().getNormalMatrix());

            NametagFixClient.deferredLabels.add(new NametagFixClient.DeferredLabel(
                    (EntityRenderer<?, ?>) (Object) this,
                    state,
                    queue,
                    cameraRenderState,
                    positionMatrix,
                    normalMatrix
            ));
        } else {
            original.call(state, matrices, queue, cameraRenderState);
        }
    }
}
