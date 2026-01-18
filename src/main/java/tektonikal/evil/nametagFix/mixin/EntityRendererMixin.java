package tektonikal.evil.nametagFix.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import tektonikal.evil.nametagFix.client.NametagFixClient;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {

    @WrapMethod(method = "renderLabelIfPresent")
    private void yeah(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Operation<Void> original) {
        if (!NametagFixClient.isItTheLastWorldRenderStage) {
            NametagFixClient.yeah.add(entity);
        } else {
            original.call(entity, text, matrices, vertexConsumers, light);
        }

    }
}
