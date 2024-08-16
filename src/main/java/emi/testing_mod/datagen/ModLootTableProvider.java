package emi.testing_mod.datagen;

import emi.testing_mod.block.ModBlocks;
import emi.testing_mod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.CRYSTAL_BLOCK, crystalOreDrops(ModBlocks.CRYSTAL_BLOCK, ModItems.CRYSTAL_SHARD,0,1));
        addDrop(ModBlocks.CRYSTAL_STAIRS, crystalOreDrops(ModBlocks.CRYSTAL_STAIRS,ModItems.CRYSTAL_SHARD,0,1));
        addDrop(ModBlocks.CRYSTAL_SLAB, crystalOreDrops(ModBlocks.CRYSTAL_SLAB,ModItems.CRYSTAL_SHARD,0,1));
        addDrop(ModBlocks.CRYSTAL_WALL, crystalOreDrops(ModBlocks.CRYSTAL_SLAB,ModItems.CRYSTAL_SHARD,0,1));
        addDrop(ModBlocks.CRYSTAL_BUTTON, crystalOreDrops(ModBlocks.CRYSTAL_SLAB,ModItems.CRYSTAL_SHARD,0,1));
        addDrop(ModBlocks.LASER_BLOCK);

    }

    public LootTable.Builder crystalOreDrops(Block drop, Item item, float min, float max) {
        return dropsWithSilkTouch(drop, (LootPoolEntry.Builder)this.applyExplosionDecay(drop,
                ItemEntry.builder(item).
                        apply(SetCountLootFunction
                                .builder(UniformLootNumberProvider
                                        .create(min, max)))
                        .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))));
    }
}
