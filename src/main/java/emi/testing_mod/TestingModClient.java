package emi.testing_mod;

import emi.testing_mod.block.ModBlocks;
import emi.testing_mod.block.entity.ModBlockEntities;
import emi.testing_mod.block.entity.renderer.LaserBlockEntityRenderer;
import emi.testing_mod.particle.ModParticles;
import emi.testing_mod.particle.custom.LaserParticle;
import emi.testing_mod.render.ModRenderLayers;
import emi.testing_mod.screen.LaserBlockScreen;
import emi.testing_mod.screen.ModScreenHandlers;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.Identifier;

public class TestingModClient implements ClientModInitializer {

    private static final ManagedShaderEffect LASER_SHADER = ShaderEffectManager.getInstance()
            .manage(new Identifier(Testing_mod.MOD_ID, "shaders/core/rendertype_energy_orb.json"));

    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CRYSTAL_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CRYSTAL_SLAB, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CRYSTAL_STAIRS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CRYSTAL_WALL, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CRYSTAL_BUTTON, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LASER_BLOCK,RenderLayer.getTranslucent());


        HandledScreens.register(ModScreenHandlers.LASER_BLOCK_SCREEN_HANDLER, LaserBlockScreen::new);

        BlockEntityRendererFactories.register(ModBlockEntities.LASER_BLOCK_ENTITY, LaserBlockEntityRenderer::new);

        ParticleFactoryRegistry.getInstance().register(ModParticles.LASER_PARTICLE, LaserParticle.Factory::new);



        //register custom shader


    }
}
