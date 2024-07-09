package emi.testing_mod.util;

import emi.testing_mod.item.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTableModifiers {
    private static final Identifier END_CITY_ID =
            new Identifier("minecraft","chests/end_city_treasure");

    public static void modifyLootTables()
    {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(END_CITY_ID.equals(id))
            {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.1f)) //drops 10% of the time
                        .with(ItemEntry.builder(ModItems.CRYSTAL_SHARD)) // item
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,2.0f)).build()); //number of items

                tableBuilder.pool(poolBuilder.build());
            }



        });
    }
}
