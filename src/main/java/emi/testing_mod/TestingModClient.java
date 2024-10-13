package emi.testing_mod;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import emi.testing_mod.block.ModBlocks;
import emi.testing_mod.block.entity.ModBlockEntities;
import emi.testing_mod.block.entity.renderer.LaserBlockEntityRenderer;
import emi.testing_mod.block.entity.renderer.ReactorCoreEntityRenderer;
import emi.testing_mod.particle.ModParticles;
import emi.testing_mod.particle.custom.LaserParticle;
import emi.testing_mod.render.ModRenderLayers;
import emi.testing_mod.screen.LaserBlockScreen;
import emi.testing_mod.screen.ReactorCoreScreen;
import emi.testing_mod.screen.ModScreenHandlers;
import ladysnake.satin.api.event.*;
import ladysnake.satin.api.experimental.ReadableDepthFramebuffer;
import ladysnake.satin.api.managed.ManagedFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.Identifier;

import java.util.EventListener;

public class TestingModClient implements ClientModInitializer {

    private static final MinecraftClient mc = MinecraftClient.getInstance();


    private static final ManagedShaderEffect BLOOM_SHADER = ShaderEffectManager.getInstance()
            .manage(new Identifier(Testing_mod.MOD_ID, "shaders/post/illusion.json"), shader -> {
                shader.setSamplerUniform("DepthBuffer", ((ReadableDepthFramebuffer)mc.getFramebuffer()).getStillDepthMap());
                shader.setSamplerUniform("depth", (mc.getFramebuffer().getDepthAttachment()));

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
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.REACTOR_CORE, RenderLayer.getTranslucent());



        HandledScreens.register(ModScreenHandlers.LASER_BLOCK_SCREEN_HANDLER, LaserBlockScreen::new);
        HandledScreens.register(ModScreenHandlers.REACTOR_CORE_SCREEN_HANDLER, ReactorCoreScreen::new);


        BlockEntityRendererFactories.register(ModBlockEntities.LASER_BLOCK_ENTITY, LaserBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.REACTOR_CORE_ENTITY, ReactorCoreEntityRenderer::new);

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


        });

        PostWorldRenderCallback.EVENT.register((camera, tickDelta, nanoTime) -> {
            MinecraftClient client = MinecraftClient.getInstance();

            laserBuffer = client.getFramebuffer();
        });

        ShaderEffectRenderCallback.EVENT.register((tickDelta)  -> {

            MinecraftClient client = MinecraftClient.getInstance();

            testBuffer = client.getFramebuffer();


            if(laserBuffer != null) {
                BLOOM_SHADER.setSamplerUniform("test", testBuffer.getColorAttachment());
            }


            BLOOM_SHADER.render(tickDelta);
            client.getFramebuffer().beginWrite(true);
            client.getFramebuffer().copyDepthFrom(laserBuffer);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE);
            bloomBuffer.draw(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight(), false);
            bloomBuffer.clear();
            client.getFramebuffer().beginWrite(false);

            RenderSystem.disableBlend();



        });

    }
}
