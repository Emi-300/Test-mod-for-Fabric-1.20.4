package emi.testing_mod.registeries.custom;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModRegisteryKeys extends RegistryKeys {

    public static final RegistryKey<Registry<ObjRegistery>> OBJ_FILES = of("obj_files");

    private static <T> RegistryKey<Registry<T>> of(String id) {
        return RegistryKey.ofRegistry(new Identifier(id));
    }

}
