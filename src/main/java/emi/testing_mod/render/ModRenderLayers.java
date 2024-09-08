package emi.testing_mod.render;

import com.mojang.blaze3d.systems.RenderSystem;
import emi.testing_mod.Testing_mod;
import emi.testing_mod.block.entity.renderer.LaserBlockEntityRenderer;
import ladysnake.satin.api.event.EntitiesPreRenderCallback;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedCoreShader;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;

import ladysnake.satin.api.managed.uniform.Uniform1f;
import ladysnake.satin.api.util.RenderLayerHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class ModRenderLayers extends RenderLayer{


    public static final ManagedCoreShader laser = ShaderEffectManager.getInstance().manageCoreShader(Objects.requireNonNull(Identifier.of(Testing_mod.MOD_ID, ModShaders.LASER_SHADER_ID)), VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL);
    private static final Uniform1f uniformGameTime = laser.findUniform1f("GameTime");
    //private static final Uniform1f core = laser.findUniform1f("Core");
    private static int ticks;


    //public static final ManagedShaderEffect laser = ShaderEffectManager.getInstance().manage(new Identifier(Testing_mod.MOD_ID,"shaders/core/rendertype_energy_orb.json"));
    public static final RenderLayer LASER_RENDER_LAYER;


    public ModRenderLayers(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
    }

    public static void registerRenderLayers()
    {
        Testing_mod.LOGGER.info("Registering mod render layers for " + Testing_mod.MOD_ID);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ticks++;
        });
        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
            uniformGameTime.set((ticks + tickDelta));
        });

        EntitiesPreRenderCallback.EVENT.register(((camera, frustum, tickDelta) -> uniformGameTime.set(ticks + tickDelta)));

    }


    static{
        LASER_RENDER_LAYER = RenderLayer.of("laser",
                VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
                VertexFormat.DrawMode.QUADS,
                786432,
                true,
                true,
                RenderLayer.MultiPhaseParameters.builder()
                        .program(new RenderPhase.ShaderProgram(laser::getProgram))
                        .transparency(TRANSLUCENT_TRANSPARENCY)
                        .target(TRANSLUCENT_TARGET)
                        .lightmap(ENABLE_LIGHTMAP)
                        .overlay(ENABLE_OVERLAY_COLOR)
                        .cull(DISABLE_CULLING)
                        .texture(RenderPhase.Textures.create()
                                .add(LaserBlockEntityRenderer.NOISE_TEXTURE_1, false, false).build())
                        .build(false));

    }
}
