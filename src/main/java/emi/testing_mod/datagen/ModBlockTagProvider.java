package emi.testing_mod.datagen;

import emi.testing_mod.block.ModBlocks;
import emi.testing_mod.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.Blocks.VULNERABLE_TO_MAGIC)
                .add(ModBlocks.CRYSTAL_BLOCK)
                .forceAddTag(BlockTags.DIAMOND_ORES)
                .forceAddTag(BlockTags.EMERALD_ORES);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.CRYSTAL_BLOCK)
                .add(ModBlocks.CRYSTAL_STAIRS)
                .add(ModBlocks.CRYSTAL_SLAB)
                .add(ModBlocks.CRYSTAL_WALL)
                .add(ModBlocks.CRYSTAL_BUTTON)
                .add(ModBlocks.LASER_BLOCK);


        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.CRYSTAL_BLOCK)
                .add(ModBlocks.CRYSTAL_STAIRS)
                .add(ModBlocks.CRYSTAL_SLAB)
                .add(ModBlocks.CRYSTAL_WALL)
                .add(ModBlocks.CRYSTAL_BUTTON)
                .add(ModBlocks.LASER_BLOCK);


        getOrCreateTagBuilder(BlockTags.WALLS)
                .add(ModBlocks.CRYSTAL_WALL);
    }
}
