package emi.testing_mod.render;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.Camera;
import net.minecraft.util.ActionResult;

public interface BlockEntityRenderCallback {
    Event<BlockEntityRenderCallback> EVENT = EventFactory.createArrayBacked(BlockEntityRenderCallback.class,
            (listeners) -> (camera,tickDelta) -> {
                for(BlockEntityRenderCallback listener : listeners)
                {
                    ActionResult result = listener.rendered(camera,tickDelta);

                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

        ActionResult rendered(Camera camera, float tickDelta);
}
