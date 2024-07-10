package emi.testing_mod;

import emi.testing_mod.structures.SkyStructures;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.StructureType;

public class TestingModStructures {

    public static StructureType<SkyStructures> SKY_STRUCTURES;

    public static void registerStructureFeatures() {

        Testing_mod.LOGGER.info("Registering mod structures for " + Testing_mod.MOD_ID);
        SKY_STRUCTURES = Registry.register(Registries.STRUCTURE_TYPE, new Identifier(Testing_mod.MOD_ID, "sky_structures"), () -> SkyStructures.CODEC);
    }

}
