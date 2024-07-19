package emi.testing_mod;

import emi.testing_mod.block.ModBlocks;
import emi.testing_mod.item.ModItemGroups;
import emi.testing_mod.item.ModItems;
import emi.testing_mod.sound.ModSounds;
import emi.testing_mod.util.ModLootTableModifiers;
import emi.testing_mod.world.gen.ModFeatures;
import emi.testing_mod.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Testing_mod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "testing_mod";

	public static final Logger LOGGER = LoggerFactory.getLogger("testing_mod");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.




			ModItemGroups.registerItemGroup();

			ModItems.registerModItems();
			ModBlocks.registerModBlocks();


			ModLootTableModifiers.modifyLootTables();
			ModSounds.registerSounds();

			TestingModStructures.registerStructureFeatures();
			ModFeatures.RegisterModFeatures();

		ModWorldGeneration.generateModWorldGen();


	}
}