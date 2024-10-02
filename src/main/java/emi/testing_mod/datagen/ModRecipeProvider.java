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
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.LASER_CORE, 1)
                .pattern("CCC")
                .pattern("IAI")
                .pattern("CCC")
                .input('A', ModItems.POLISHED_CRYSTAL_SHARD)
                .input('I', Items.IRON_INGOT)
                .input('C', Items.COPPER_INGOT)
                .criterion(hasItem(ModItems.POLISHED_CRYSTAL_SHARD), conditionsFromItem(ModItems.POLISHED_CRYSTAL_SHARD))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.LENS, 1)
                .pattern(" I ")
                .pattern("IPI")
                .pattern(" I ")
                .input('I', Items.IRON_INGOT)
                .input('P', Items.GLASS_PANE)
                .criterion(hasItem(Items.GLASS_PANE), conditionsFromItem(Items.GLASS_PANE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.LOGIC_CONTROLLER, 1)
                .pattern("RGR")
                .pattern("ECE")
                .pattern("III")
                .input('I', Items.IRON_INGOT)
                .input('R', Items.REDSTONE)
                .input('E', Items.REPEATER)
                .input('C', Items.COMPARATOR)
                .input('G', Items.GREEN_DYE)
                .criterion(hasItem(Items.REDSTONE), conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.LASER_BLOCK, 1)
                .pattern("IPI")
                .pattern("OCL")
                .pattern("IPI")
                .input('I', Items.IRON_INGOT)
                .input('O', ModItems.LOGIC_CONTROLLER)
                .input('C', ModItems.LASER_CORE)
                .input('L', ModItems.LENS)
                .input('P', ModItems.POLISHED_CRYSTAL_SHARD)
                .criterion(hasItem(ModItems.LASER_CORE), conditionsFromItem(ModItems.LASER_CORE))
                .offerTo(exporter);
    }
}
