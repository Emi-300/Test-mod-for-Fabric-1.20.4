package emi.testing_mod.block.entity;

import emi.testing_mod.Testing_mod;
import emi.testing_mod.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {

    public static final BlockEntityType<LaserBlockEntity> LASER_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Testing_mod.MOD_ID,"laser_block_be"),
                    BlockEntityType.Builder.create(LaserBlockEntity::new,
                            ModBlocks.LASER_BLOCK).build());

    public static void RegisterBlockEntities(){
        Testing_mod.LOGGER.info("Registering Block Entities for " + Testing_mod.MOD_ID);
    }
}

