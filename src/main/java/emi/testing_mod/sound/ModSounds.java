package emi.testing_mod.sound;

import emi.testing_mod.Testing_mod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static final SoundEvent CRYSTAL_SWORD_SUMMON = registerSoundEvent("tutorial_mod:crystal_sword_summon");
    public static final SoundEvent CRYSTAL_SWORD_SWING = registerSoundEvent("tutorial_mod:crystal_sword_swing");
    public static final SoundEvent CRYSTAL_SWORD_SMASH = registerSoundEvent("crystal_sword_smash");

    private static SoundEvent registerSoundEvent(String name){
        Identifier id = new Identifier(name);

        return Registry.register(Registries.SOUND_EVENT,id,SoundEvent.of(id));
    }


    public static void registerSounds()
    {
        Testing_mod.LOGGER.info("Registering Sounds for " + Testing_mod.MOD_ID);
    }
}
