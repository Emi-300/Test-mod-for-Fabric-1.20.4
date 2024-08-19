package emi.testing_mod.render;

import emi.testing_mod.Testing_mod;
import ladysnake.satin.api.managed.ManagedCoreShader;
import ladysnake.satin.api.managed.ShaderEffectManager;

import ladysnake.satin.api.util.RenderLayerHelper;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.util.Identifier;

public class ModRenderLayers extends RenderLayer{


    public static final ManagedCoreShader laser = ShaderEffectManager.getInstance().manageCoreShader(new Identifier(Testing_mod.MOD_ID,ModShaders.LASER_SHADER_ID), VertexFormats.POSITION);
    public static final RenderLayer LASER_RENDER_LAYER;

    public ModRenderLayers(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }

    public static void registerRenderLayers()
    {
        Testing_mod.LOGGER.info("Registering mod render layers for " + Testing_mod.MOD_ID);


    }

    static{
        LASER_RENDER_LAYER = RenderLayer.of("laser",
                VertexFormats.POSITION,
                VertexFormat.DrawMode.QUADS,
                1536,
                false,
                true,
                RenderLayer.MultiPhaseParameters.builder()
                        .program(new RenderPhase.ShaderProgram(laser::getProgram))
                        .texture(RenderPhase.Textures.create()
                                .add(EndPortalBlockEntityRenderer.SKY_TEXTURE, false, false)
                                .add(EndPortalBlockEntityRenderer.PORTAL_TEXTURE, false, false).build())
                        .build(false));

    }
}
