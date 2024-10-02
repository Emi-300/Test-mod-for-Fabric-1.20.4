package emi.testing_mod;

import emi.testing_mod.structures.CrystalGrowthStructures;
import emi.testing_mod.structures.SkyStructures;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.StructureType;

public class TestingModStructures {

    public static StructureType<SkyStructures> SKY_STRUCTURES;
    public static StructureType<CrystalGrowthStructures> CRYSTAL_GROWTH_STRUCTURES;

    public static void registerStructureFeatures() {

        Testing_mod.LOGGER.info("Registering mod structures for " + Testing_mod.MOD_ID);
        SKY_STRUCTURES = Registry.register(Registries.STRUCTURE_TYPE, new Identifier(Testing_mod.MOD_ID, "sky_structures"), () -> SkyStructures.CODEC);
        CRYSTAL_GROWTH_STRUCTURES = Registry.register(Registries.STRUCTURE_TYPE, new Identifier(Testing_mod.MOD_ID, "crystal_growth_structures"), () -> CrystalGrowthStructures.CODEC);

    }

}
