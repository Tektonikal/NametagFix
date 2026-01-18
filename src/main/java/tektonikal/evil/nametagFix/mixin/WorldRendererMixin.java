package tektonikal.evil.nametagFix.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static tektonikal.evil.nametagFix.client.NametagFixClient.isItTheLastWorldRenderStage;
import static tektonikal.evil.nametagFix.client.NametagFixClient.yeah;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderChunkDebugInfo(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/Camera;)V"))
    private void ough(RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci, @Local MatrixStack matrixStack) {
        isItTheLastWorldRenderStage = true;
        for (Entity entity : yeah) {
            matrixStack.push();
            double d = MathHelper.lerp(tickCounter.getTickDelta(true), entity.lastRenderX, entity.getX());
            double e = MathHelper.lerp(tickCounter.getTickDelta(true), entity.lastRenderY, entity.getY());
            double f = MathHelper.lerp(tickCounter.getTickDelta(true), entity.lastRenderZ, entity.getZ());
            matrixStack.translate(d - camera.getPos().x, e - camera.getPos().y, f - camera.getPos().z);
            ((Yeah) MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(entity)).yeah(entity, entity.getDisplayName(), matrixStack, MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers(), MinecraftClient.getInstance().getEntityRenderDispatcher().getLight(entity, tickCounter.getTickDelta(true)), tickCounter.getTickDelta(true));
            matrixStack.pop();
        }
        yeah.clear();
        isItTheLastWorldRenderStage = false;
    }
}
