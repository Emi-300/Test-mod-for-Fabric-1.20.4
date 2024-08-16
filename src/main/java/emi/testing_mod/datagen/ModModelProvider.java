package emi.testing_mod.datagen;

import emi.testing_mod.block.ModBlocks;
import emi.testing_mod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        BlockStateModelGenerator.BlockTexturePool crystalPool =  blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.CRYSTAL_BLOCK);
        crystalPool.stairs(ModBlocks.CRYSTAL_STAIRS);
        crystalPool.slab(ModBlocks.CRYSTAL_SLAB);
        crystalPool.wall(ModBlocks.CRYSTAL_WALL);
        crystalPool.button(ModBlocks.CRYSTAL_BUTTON);



    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.CRYSTAL_SHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.POLISHED_CRYSTAL_SHARD, Models.GENERATED);
    }
}
