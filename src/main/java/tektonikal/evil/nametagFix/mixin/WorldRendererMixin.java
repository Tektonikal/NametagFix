package tektonikal.evil.nametagFix.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.render.command.OrderedRenderCommandQueueImpl;
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
        if (NametagFixClient.deferredLabels.isEmpty()) {
            return;
        }
        NametagFixClient.isItTheLastWorldRenderStage = true;

        MinecraftClient.getInstance().inGameHud.render();

        for (NametagFixClient.DeferredLabel label : NametagFixClient.deferredLabels) {
            matrixStack.push();
            matrixStack.peek().getPositionMatrix().set(label.positionMatrix());
            matrixStack.peek().getNormalMatrix().set(label.normalMatrix());

            ((Yeah) label.renderer()).yeah(
                    label.state(),
                    matrixStack,
                    label.queue(),
                    label.cameraRenderState()
            );

            matrixStack.pop();
        }

        immediate.draw();

        NametagFixClient.deferredLabels.clear();
        NametagFixClient.isItTheLastWorldRenderStage = false;
    }
}
