package tektonikal.evil.nametagFix.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.render.entity.EntityRenderer;
import tektonikal.evil.nametagFix.client.NametagFixClient;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<S extends EntityRenderState> {

    @WrapMethod(method = "renderLabelIfPresent")
    private void yeah(S state, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Operation<Void> original) {
        if (!NametagFixClient.isItTheLastWorldRenderStage) {
            Matrix4f positionMatrix = new Matrix4f(matrices.peek().getPositionMatrix());
            Matrix4f normalMatrix = new Matrix4f(matrices.peek().getNormalMatrix());

            NametagFixClient.deferredLabels.add(new NametagFixClient.DeferredLabel(
                    (EntityRenderer<?, ?>) (Object) this,
                    state,
                    text,
                    light,
                    positionMatrix,
                    normalMatrix
            ));
        } else {
            original.call(state, text, matrices, vertexConsumers, light);
        }
    }
}
