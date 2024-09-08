package emi.testing_mod;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import emi.testing_mod.block.ModBlocks;
import emi.testing_mod.block.entity.ModBlockEntities;
import emi.testing_mod.block.entity.renderer.LaserBlockEntityRenderer;
import emi.testing_mod.particle.ModParticles;
import emi.testing_mod.particle.custom.LaserParticle;
import emi.testing_mod.render.ModRenderLayers;
import emi.testing_mod.render.ModShaders;
import emi.testing_mod.screen.LaserBlockScreen;
import emi.testing_mod.screen.ModScreenHandlers;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedCoreShader;
import ladysnake.satin.api.managed.ManagedFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.managed.uniform.Uniform1f;
import ladysnake.satin.api.util.RenderLayerHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class TestingModClient implements ClientModInitializer {

    private static final ManagedShaderEffect BLOOM_SHADER = ShaderEffectManager.getInstance()
            .manage(new Identifier(Testing_mod.MOD_ID, "shaders/post/test.json"));

    public static final ManagedFramebuffer bloomBuffer = BLOOM_SHADER.getTarget("final");


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


        RenderLayer blockRenderLayer = bloomBuffer.getRenderLayer(ModRenderLayers.LASER_RENDER_LAYER);

        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {

            MinecraftClient client = MinecraftClient.getInstance();
            BLOOM_SHADER.render(tickDelta);
            client.getFramebuffer().beginWrite(true);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE);
            bloomBuffer.draw(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight(), false);
            bloomBuffer.clear();
            client.getFramebuffer().beginWrite(true);
            RenderSystem.disableBlend();
        });

    }
}
