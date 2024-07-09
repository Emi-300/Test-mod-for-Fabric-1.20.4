package emi.testing_mod.block;

import emi.testing_mod.Testing_mod;
import emi.testing_mod.block.custom.CrystalBlock;
import net.fabricmc.fabric.api.block.v1.FabricBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;


public class ModBlocks {

    public static final Block CRYSTAL_BLOCK = registerBlock("crystal_block",
            new CrystalBlock(FabricBlockSettings.copyOf(Blocks.GLASS).luminance(50).strength(1.0f).sounds(BlockSoundGroup.AMETHYST_BLOCK).nonOpaque().requiresTool()));
    public static final Block CRYSTAL_STAIRS = registerBlock("crystal_stairs",
            new StairsBlock(ModBlocks.CRYSTAL_BLOCK.getDefaultState(), FabricBlockSettings.copyOf(Blocks.GLASS).luminance(50).strength(1.0f).sounds(BlockSoundGroup.AMETHYST_BLOCK).nonOpaque().requiresTool()));
    public static final Block CRYSTAL_SLAB = registerBlock("crystal_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.GLASS).luminance(50).strength(1.0f).sounds(BlockSoundGroup.AMETHYST_BLOCK).nonOpaque().requiresTool()));
    public static final Block CRYSTAL_WALL = registerBlock("crystal_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.GLASS).luminance(50).strength(1.0f).sounds(BlockSoundGroup.AMETHYST_BLOCK).nonOpaque().requiresTool()));
    public static final Block CRYSTAL_BUTTON = registerBlock("crystal_button",
            new ButtonBlock(BlockSetType.IRON, 10, FabricBlockSettings.copyOf(Blocks.GLASS).luminance(50).strength(1.0f).sounds(BlockSoundGroup.AMETHYST_BLOCK).nonOpaque().requiresTool()));


    private static Block registerBlock(String name, Block block)
    {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(Testing_mod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block)
    {
        return Registry.register(Registries.ITEM, new Identifier(Testing_mod.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks()
    {
        Testing_mod.LOGGER.info("Registering mod blocks for " + Testing_mod.MOD_ID);
    }
}
