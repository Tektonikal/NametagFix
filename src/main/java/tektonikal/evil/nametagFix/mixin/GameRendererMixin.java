package tektonikal.evil.nametagFix.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/state/GuiRenderState;clear()V", shift = At.Shift.AFTER))
    private void gameRender(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci, @Local DrawContext drawContext) {
        drawContext.fill(0, 0, 100, 100, 0xFF00FFFF);
    }
}
