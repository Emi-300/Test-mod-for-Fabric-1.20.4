package emi.testing_mod.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.resource.ResourceManager;

public class ModShaderPrograms extends GameRenderer {

    public ModShaderPrograms(MinecraftClient client, HeldItemRenderer heldItemRenderer, ResourceManager resourceManager, BufferBuilderStorage buffers) {
        super(client, heldItemRenderer, resourceManager, buffers);
    }


}
