package emi.testing_mod;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import emi.testing_mod.block.ModBlocks;
import emi.testing_mod.block.entity.LaserBlockEntity;
import emi.testing_mod.block.entity.ModBlockEntities;
import emi.testing_mod.block.entity.renderer.LaserBlockEntityRenderer;
import emi.testing_mod.particle.ModParticles;
import emi.testing_mod.particle.custom.LaserParticle;
import emi.testing_mod.render.ModRenderLayers;
import emi.testing_mod.render.ModShaders;
import emi.testing_mod.screen.LaserBlockScreen;
import emi.testing_mod.screen.ModScreenHandlers;
import ladysnake.satin.api.event.*;
import ladysnake.satin.api.experimental.ReadableDepthFramebuffer;
import ladysnake.satin.api.managed.ManagedCoreShader;
import ladysnake.satin.api.managed.ManagedFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.managed.uniform.Uniform1f;
import ladysnake.satin.api.util.RenderLayerHelper;
import ladysnake.satin.impl.FramebufferWrapper;
import ladysnake.satin.mixin.client.render.RenderLayerAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class TestingModClient implements ClientModInitializer {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    private static final ManagedShaderEffect BLOOM_SHADER = ShaderEffectManager.getInstance()
            .manage(new Identifier(Testing_mod.MOD_ID, "shaders/post/test.json"), shader -> {
                shader.setSamplerUniform("DepthBuffer", (mc.getFramebuffer()));


            });


    public static final ManagedFramebuffer bloomBuffer = BLOOM_SHADER.getTarget("final");

    public static  ManagedFramebuffer laserFrameBuffer = BLOOM_SHADER.getTarget("swap");
    public static Framebuffer laserBuffer;
    public static Framebuffer testBuffer;


    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CRYSTAL_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CRYSTAL_SLAB, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CRYSTAL_STAIRS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CRYSTAL_WALL, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CRYSTAL_BUTTON, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LASER_BLOCK, RenderLayer.getTranslucent());


        HandledScreens.register(ModScreenHandlers.LASER_BLOCK_SCREEN_HANDLER, LaserBlockScreen::new);

        BlockEntityRendererFactories.register(ModBlockEntities.LASER_BLOCK_ENTITY, LaserBlockEntityRenderer::new);

        ParticleFactoryRegistry.getInstance().register(ModParticles.LASER_PARTICLE, LaserParticle.Factory::new);


        //register custom shader
        ModRenderLayers.registerRenderLayers();


//        EntitiesPostRenderCallback.EVENT.register((camera, frustum, tickDelta) -> {
//
//            MinecraftClient client = MinecraftClient.getInstance();
//            laserBuffer = client.getFramebuffer();
//
//        });

        HudRenderCallback.EVENT.register((drawContext,tickDelta) ->{
            MinecraftClient client = MinecraftClient.getInstance();

            laserBuffer = client.getFramebuffer();

        });

        PostWorldRenderCallback.EVENT.register((camera, tickDelta, nanoTime) -> {
            MinecraftClient client = MinecraftClient.getInstance();

            testBuffer = client.getFramebuffer();
        });

        ShaderEffectRenderCallback.EVENT.register((tickDelta)  -> {

            MinecraftClient client = MinecraftClient.getInstance();

            if(laserBuffer != null) {
                BLOOM_SHADER.setSamplerUniform("test", testBuffer.getColorAttachment());
            }


            BLOOM_SHADER.render(tickDelta);
            client.getFramebuffer().beginWrite(true);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE);
            bloomBuffer.draw(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight(), false);
            bloomBuffer.clear();
            client.getFramebuffer().beginWrite(false);

            RenderSystem.disableBlend();


        });

    }
}
