package tektonikal.evil.nametagFix.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tektonikal.evil.nametagFix.client.NametagFixClient;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    // Inject at the END of the renderMain lambda, AFTER everything else including immediate.draw()
    @Inject(method = "method_62214", at = @At("TAIL"))
    private void renderDeferredNametags(CallbackInfo ci, @Local MatrixStack matrixStack, @Local VertexConsumerProvider.Immediate immediate) {
        System.out.println("[NametagFix] renderDeferredNametags called, deferred count: " + NametagFixClient.deferredLabels.size());

        if (NametagFixClient.deferredLabels.isEmpty()) {
            return;
        }

        NametagFixClient.isItTheLastWorldRenderStage = true;

        for (NametagFixClient.DeferredLabel label : NametagFixClient.deferredLabels) {
            matrixStack.push();
            matrixStack.peek().getPositionMatrix().set(label.positionMatrix());
            matrixStack.peek().getNormalMatrix().set(label.normalMatrix());

            ((Yeah) label.renderer()).yeah(
                    label.state(),
                    label.text(),
                    matrixStack,
                    immediate,
                    label.light()
            );

            matrixStack.pop();
        }

        immediate.draw();

        NametagFixClient.deferredLabels.clear();
        NametagFixClient.isItTheLastWorldRenderStage = false;
    }
}
