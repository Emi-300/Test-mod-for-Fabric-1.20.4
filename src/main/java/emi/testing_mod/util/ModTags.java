package emi.testing_mod.util;

import emi.testing_mod.Testing_mod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {

    public static class Blocks{
        public static final TagKey<Block> VULNERABLE_TO_MAGIC = createTag("vulnerable_to_magic");

        private static TagKey<Block> createTag(String name)
        {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(Testing_mod.MOD_ID, name));
        }
    }

    public static class Items{
        private static TagKey<Item> createTag(String name)
        {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(Testing_mod.MOD_ID, name));
        }
    }
}
