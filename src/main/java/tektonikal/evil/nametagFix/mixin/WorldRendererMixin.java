package tektonikal.evil.nametagFix.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.command.OrderedRenderCommandQueueImpl;
import net.minecraft.client.render.command.RenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderManager;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.render.state.WorldRenderState;
import net.minecraft.client.util.Handle;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.text.Text;
import net.minecraft.util.profiler.Profiler;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tektonikal.evil.nametagFix.client.NametagFixClient;

import java.util.Comparator;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @Shadow
    @Final
    private RenderDispatcher entityRenderDispatcher;

    @Shadow
    @Final
    private EntityRenderManager entityRenderManager;

    @Shadow
    @Final
    private BufferBuilderStorage bufferBuilders;

    @Shadow
    @Final
    private OrderedRenderCommandQueueImpl entityRenderCommandQueue;


    @Shadow
    protected abstract void renderWeather(FrameGraphBuilder frameGraphBuilder, GpuBufferSlice gpuBufferSlice);

    @Inject(method = "method_62214", at = @At(value = "TAIL"))
    private void rightBeforeRenderEnd(GpuBufferSlice gpuBufferSlice, WorldRenderState worldRenderState, Profiler profiler, Matrix4f matrix4f, Handle handle, Handle handle2, boolean bl, Handle handle3, Handle handle4, CallbackInfo ci) {
        var immediate = this.bufferBuilders.getEntityVertexConsumers();
        NametagFixClient.deferredSeethroughLabels.sort(Comparator.comparing(NametagFixClient.DeferredLabel::distanceToCameraSq).reversed());
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        for (NametagFixClient.DeferredLabel labelCommand : NametagFixClient.deferredSeethroughLabels) {
            textRenderer.draw(
                    labelCommand.text(),
                    labelCommand.x(),
                    labelCommand.y(),
                    labelCommand.color(),
                    false,
                    labelCommand.matricesEntry(),
                    immediate,
                    TextRenderer.TextLayerType.SEE_THROUGH,
                    labelCommand.backgroundColor(),
                    labelCommand.lightCoords()
            );
        }

        for (NametagFixClient.DeferredLabel labelCommand : NametagFixClient.deferredNormalLabels) {
            textRenderer.draw(
                    labelCommand.text(),
                    labelCommand.x(),
                    labelCommand.y(),
                    labelCommand.color(),
                    false,
                    labelCommand.matricesEntry(),
                    immediate,
                    TextRenderer.TextLayerType.NORMAL,
                    labelCommand.backgroundColor(),
                    labelCommand.lightCoords()
            );
        }
        NametagFixClient.clear();
        immediate.draw();
    }
}
