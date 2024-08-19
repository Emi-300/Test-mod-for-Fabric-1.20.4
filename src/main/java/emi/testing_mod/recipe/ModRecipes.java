package emi.testing_mod.recipe;

import emi.testing_mod.Testing_mod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static void registerRecipes()
    {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Testing_mod.MOD_ID, LaserRecipe.Serializer.ID),
                LaserRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(Testing_mod.MOD_ID, LaserRecipe.Type.ID),
                LaserRecipe.Type.INSTANCE);
    }
}
