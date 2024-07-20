package emi.testing_mod.world.biome;

import emi.testing_mod.Testing_mod;
import emi.testing_mod.world.biome.surface.ModMaterialRules;
import net.minecraft.util.Identifier;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class ModTerrablenderAPI implements TerraBlenderApi {

    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new ModOverworldRegion(new Identifier(Testing_mod.MOD_ID, "overworld"), 4));
        Regions.register(new ModEndRegion(new Identifier(Testing_mod.MOD_ID, "end"), 4));

        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Testing_mod.MOD_ID, ModMaterialRules.makeRules());
    }




}
