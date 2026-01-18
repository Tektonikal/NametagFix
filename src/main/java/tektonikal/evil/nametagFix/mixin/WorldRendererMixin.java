package tektonikal.evil.nametagFix.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static tektonikal.evil.nametagFix.client.NametagFixClient.isItTheLastWorldRenderStage;
import static tektonikal.evil.nametagFix.client.NametagFixClient.yeah;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderChunkDebugInfo(Lnet/minecraft/client/render/Camera;)V"))
    private void ough(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, CallbackInfo ci) {
        isItTheLastWorldRenderStage = true;
        for (Entity entity : yeah) {
            matrices.push();
            double d = MathHelper.lerp(tickDelta, entity.lastRenderX, entity.getX());
            double e = MathHelper.lerp(tickDelta, entity.lastRenderY, entity.getY());
            double f = MathHelper.lerp(tickDelta, entity.lastRenderZ, entity.getZ());
            matrices.translate(d - camera.getPos().x, e - camera.getPos().y, f - camera.getPos().z);
            ((Yeah) MinecraftClient.getInstance().getEntityRenderManager().getRenderer(entity)).yeah(entity, entity.getDisplayName(), matrices, MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers(), MinecraftClient.getInstance().getEntityRenderManager().getLight(entity, tickDelta));
            matrices.pop();
        }
        yeah.clear();
        isItTheLastWorldRenderStage = false;
    }
}
