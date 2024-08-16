package emi.testing_mod.item;

import emi.testing_mod.Testing_mod;
import emi.testing_mod.block.ModBlocks;
import emi.testing_mod.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup TEST_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(Testing_mod.MOD_ID, "test_group"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable( "itemgroup.test_group"))
                    .icon(() -> new ItemStack(ModItems.CRYSTAL_SHARD))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.CRYSTAL_SHARD);
                        entries.add(ModItems.POLISHED_CRYSTAL_SHARD);
                        entries.add(ModItems.CRYSTAL_SWORD);

                        entries.add(ModBlocks.CRYSTAL_BLOCK);
                        entries.add(ModBlocks.CRYSTAL_STAIRS);
                        entries.add(ModBlocks.CRYSTAL_SLAB);
                        entries.add(ModBlocks.CRYSTAL_WALL);
                        entries.add(ModBlocks.CRYSTAL_BUTTON);

                        entries.add(ModBlocks.LASER_BLOCK);


                    }).build());

    public static void registerItemGroup()
    {
        Testing_mod.LOGGER.info("Registering item groups for " + Testing_mod.MOD_ID);

    }
}
