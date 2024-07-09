package emi.testing_mod.item;

import emi.testing_mod.Testing_mod;
import emi.testing_mod.item.custom.CrystalShardItem;
import emi.testing_mod.item.custom.CrystalSwordItem;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;



public class ModItems {

    public static final Item CRYSTAL_SHARD = registerItem("crystal_shard", new CrystalShardItem(new Item.Settings()));
    public static final Item POLISHED_CRYSTAL_SHARD = registerItem("polished_crystal_shard", new Item(new Item.Settings()));

    public static final Item CRYSTAL_SWORD = registerItem("crystal_sword", new CrystalSwordItem(ModToolMaterial.CRYSTAL,new Item.Settings().maxCount(1)));

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries)
    {
        entries.add(CRYSTAL_SHARD);
        entries.add(POLISHED_CRYSTAL_SHARD);
    }
    private static Item registerItem(String name, Item item)
    {
        return Registry.register(Registries.ITEM, new Identifier(Testing_mod.MOD_ID, name), item);
    }



    public static void registerModItems() {
        Testing_mod.LOGGER.debug("Registering mod items for " + Testing_mod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}
