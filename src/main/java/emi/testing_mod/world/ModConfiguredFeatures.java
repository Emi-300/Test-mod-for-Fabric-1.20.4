package emi.testing_mod.world;

import emi.testing_mod.Testing_mod;
import emi.testing_mod.block.ModBlocks;
import emi.testing_mod.world.biome.ModBiomes;
import emi.testing_mod.world.gen.feature.CrystalGrowthFeature;
import net.minecraft.block.Blocks;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;

import java.util.List;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?,?>> CRYSTAL_OVERWORLD_KEY = registerKey("overworld_crystal_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> CRYSTAL_END_KEY = registerKey("end_crystal_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> CRYSTAL_GROWTH_KEY = registerKey("crystal_growth");

    public static final Feature<DefaultFeatureConfig> CRYSTAL_GROWTH = Registry.register(Registries.FEATURE, new Identifier(Testing_mod.MOD_ID, "crystal_growth_feature"), new CrystalGrowthFeature(DefaultFeatureConfig.CODEC));

    public static void bootstrap(Registerable<ConfiguredFeature<?,?>> context)
    {
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest endReplaceables = new BlockMatchRuleTest(Blocks.END_STONE);

        List<OreFeatureConfig.Target> overworldCrystalOres =
                List.of(OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.CRYSTAL_BLOCK.getDefaultState()));
                        //add other orefeatureconfigs after ^- the ruletest      ^- the block to replace it with

        List<OreFeatureConfig.Target> endCrystalOres =
                List.of(OreFeatureConfig.createTarget(endReplaceables, ModBlocks.CRYSTAL_BLOCK.getDefaultState()));

        register(context, CRYSTAL_OVERWORLD_KEY, Feature.ORE, new OreFeatureConfig(overworldCrystalOres, 1));
        register(context, CRYSTAL_END_KEY, Feature.ORE, new OreFeatureConfig(endCrystalOres, 12));


        register(context, CRYSTAL_GROWTH_KEY, CRYSTAL_GROWTH, new DefaultFeatureConfig());
    }

    public static RegistryKey<ConfiguredFeature<?,?>> registerKey(String name){
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE,new Identifier(Testing_mod.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?,?>> context, RegistryKey<ConfiguredFeature<?,?>> key, F feature, FC configuration)
    {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }




}
