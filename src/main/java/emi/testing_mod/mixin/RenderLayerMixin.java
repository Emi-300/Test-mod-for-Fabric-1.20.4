package emi.testing_mod.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(RenderSystem.class)
public class RenderLayerMixin {

    //@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderSystem;renderLayer"), method = "render",cancellable = false)
    //private void onRenderLayer(final Camera camera, float )
}
