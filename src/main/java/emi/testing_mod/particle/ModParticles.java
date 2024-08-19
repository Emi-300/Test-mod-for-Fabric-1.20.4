package emi.testing_mod.particle;

import emi.testing_mod.Testing_mod;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {

    public static final DefaultParticleType LASER_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType LASER_OFFSHOOT_PARTICLE = FabricParticleTypes.simple();

    public static void RegisterParticles() {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Testing_mod.MOD_ID,"laser_particle"),
                LASER_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Testing_mod.MOD_ID,"laser_offshoot_particle"),
                LASER_OFFSHOOT_PARTICLE);
    }

    public static void registerModParticles()
    {
        Testing_mod.LOGGER.info("Registering mod particles for " + Testing_mod.MOD_ID);
        RegisterParticles();
    }
}
