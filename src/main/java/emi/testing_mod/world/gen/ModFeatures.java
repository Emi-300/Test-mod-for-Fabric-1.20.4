package emi.testing_mod.world.gen;

import emi.testing_mod.Testing_mod;
import emi.testing_mod.world.gen.feature.CrystalGrowthFeature;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.IceSpikeFeature;

public class ModFeatures {

    public static final Feature CRYSTAL_GROWTH_FEATURE = registerFeature("crystal_growth", new CrystalGrowthFeature(DefaultFeatureConfig.CODEC));

    private static Feature registerFeature(String name, Feature feature)
    {
        return Registry.register(Registries.FEATURE, new Identifier(Testing_mod.MOD_ID, name), feature);
    }

    public static void RegisterModFeatures()
    {
        Testing_mod.LOGGER.info("Registering mod features for " + Testing_mod.MOD_ID);

    }



}
