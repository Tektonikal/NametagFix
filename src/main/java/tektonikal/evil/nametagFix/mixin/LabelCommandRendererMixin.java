package tektonikal.evil.nametagFix.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.BatchingRenderCommandQueue;
import net.minecraft.client.render.command.LabelCommandRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import tektonikal.evil.nametagFix.client.NametagFixClient;

@Mixin(LabelCommandRenderer.class)
public class LabelCommandRendererMixin {

    @WrapMethod(method = "render")
    private void ough(BatchingRenderCommandQueue queue, VertexConsumerProvider.Immediate vertexConsumers, TextRenderer renderer, Operation<Void> original) {
        //NOP
    }

    @Mixin(LabelCommandRenderer.Commands.class)
    public static class LabelCommandRendererCommandMixin {

        @WrapMethod(method = "add")
        private void deferLabelAdd(MatrixStack matrices, @Nullable Vec3d pos, int y, Text label, boolean notSneaking, int light, double squaredDistanceToCamera, CameraRenderState cameraState, Operation<Void> original) {
            if (pos != null) {
                MinecraftClient client = MinecraftClient.getInstance();
                matrices.push();
                matrices.translate(pos.x, pos.y + 0.5, pos.z);
                matrices.multiply(cameraState.orientation);
                matrices.scale(0.025F, -0.025F, 0.025F);

                Matrix4f matrix4f = new Matrix4f(matrices.peek().getPositionMatrix());
                float x = -client.textRenderer.getWidth(label) / 2.0F;
                int backgroundColor = (int) (client.options.getTextBackgroundOpacity(0.25F) * 255.0F) << 24;

                if (notSneaking) {
                    NametagFixClient.deferredNormalLabels.add(new NametagFixClient.DeferredLabel(matrix4f, x, y, label, LightmapTextureManager.applyEmission(light, 2), -1, 0, squaredDistanceToCamera));
                    NametagFixClient.deferredSeethroughLabels.add(new NametagFixClient.DeferredLabel(new Matrix4f(matrix4f), x, y, label, light, -2130706433, backgroundColor, squaredDistanceToCamera));
                } else {
                    NametagFixClient.deferredNormalLabels.add(new NametagFixClient.DeferredLabel(matrix4f, x, y, label, light, -2130706433, backgroundColor, squaredDistanceToCamera));
                }

                matrices.pop();
            }
        }
    }
}
