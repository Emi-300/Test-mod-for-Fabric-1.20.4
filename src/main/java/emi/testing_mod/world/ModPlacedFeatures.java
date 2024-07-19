package emi.testing_mod.world;

import emi.testing_mod.Testing_mod;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;


public class ModPlacedFeatures {

    public static final RegistryKey<PlacedFeature> CRYSTAL_OVERWORLD_PLACED_KEY = registerKey("crystal_overworld_placed");
    public static final RegistryKey<PlacedFeature> CRYSTAL_END_PLACED_KEY = registerKey("crystal_end_placed");

    public static final RegistryKey<PlacedFeature> CRYSTAL_GROWTH_PLACED_KEY = registerKey("crystal_growth");


    public static void bootstrap(Registerable<PlacedFeature> context){
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, CRYSTAL_OVERWORLD_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.CRYSTAL_OVERWORLD_KEY),
                ModOrePlacement.modifiersWithCount(1, //veins per chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80),YOffset.fixed(10))));

        register(context, CRYSTAL_END_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.CRYSTAL_END_KEY),
                ModOrePlacement.modifiersWithCount(12, //veins per chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80),YOffset.fixed(80))));

        register(context, CRYSTAL_GROWTH_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.CRYSTAL_GROWTH_KEY), List.of(CountPlacementModifier.of(3), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of()));
    }

    public static RegistryKey<PlacedFeature> registerKey(String name){
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(Testing_mod.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?,?>> configuration, List<PlacementModifier> modifiers)
    {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
