package emi.testing_mod.datagen;

import emi.testing_mod.block.ModBlocks;
import emi.testing_mod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        offer2x2CompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRYSTAL_BLOCK, ModItems.POLISHED_CRYSTAL_SHARD);

        offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,ModBlocks.CRYSTAL_SLAB,ModBlocks.CRYSTAL_BLOCK);
        offerWallRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,ModBlocks.CRYSTAL_WALL,ModBlocks.CRYSTAL_BLOCK);
        createStairsRecipe(ModBlocks.CRYSTAL_STAIRS, Ingredient.ofItems(ModBlocks.CRYSTAL_BLOCK)).criterion(hasItem(ModBlocks.CRYSTAL_BLOCK),conditionsFromItem(ModBlocks.CRYSTAL_BLOCK)).offerTo(exporter, new Identifier(getRecipeName(ModBlocks.CRYSTAL_STAIRS)));

        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,ModBlocks.CRYSTAL_SLAB,ModBlocks.CRYSTAL_BLOCK);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,ModBlocks.CRYSTAL_WALL,ModBlocks.CRYSTAL_BLOCK);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS,ModBlocks.CRYSTAL_STAIRS,ModBlocks.CRYSTAL_BLOCK);
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.CRYSTAL_SWORD, 1)
                .pattern("ABA")
                .pattern("ABA")
                .pattern("ACA")
                .input('A', ModItems.POLISHED_CRYSTAL_SHARD)
                .input('B', Items.NETHERITE_INGOT)
                .input('C', Items.GOLD_INGOT)
                .criterion(hasItem(ModItems.CRYSTAL_SHARD), conditionsFromItem(ModItems.CRYSTAL_SHARD))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.CRYSTAL_SWORD)));
    }
}
