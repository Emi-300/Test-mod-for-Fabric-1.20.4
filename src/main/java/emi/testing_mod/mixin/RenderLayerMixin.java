package emi.testing_mod.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.Camera;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(RenderSystem.class)
public class RenderLayerMixin {

   // @Inject(at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/api/client/rendering/v1/WorldRenderEvents;onLast(Lnet/fabricmc/fabric/api/client/rendering/v1/WorldRenderContext;)V"), method = "onRenderLayer(Lnet/minecraft/client/render/CameraF;)V",cancellable = false)
    private void onRenderLayer(final Camera camera, float tickDelta)
    {
        ActionResult result;
    }
}
