package emi.testing_mod.screen;

import emi.testing_mod.Testing_mod;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {

    public static final ScreenHandlerType<LaserBlockScreenHandler> LASER_BLOCK_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER,new Identifier(Testing_mod.MOD_ID,"laser_screen"),
                    new ExtendedScreenHandlerType<>(LaserBlockScreenHandler::new));

    public static void registerScreenHandlers(){
        Testing_mod.LOGGER.info("Registering screen handlers for " + Testing_mod.MOD_ID);
    }
}
