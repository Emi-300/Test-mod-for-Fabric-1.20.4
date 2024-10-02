package emi.testing_mod.world.gen.feature;

import com.mojang.serialization.Codec;
import emi.testing_mod.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class CrystalGrowthFeature extends Feature<DefaultFeatureConfig> {
    public CrystalGrowthFeature(Codec<DefaultFeatureConfig> codec){super(codec);}

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        BlockPos blockPos = context.getOrigin();
        Random random = context.getRandom();

        StructureWorldAccess structureWorldAccess;
        for(structureWorldAccess = context.getWorld(); structureWorldAccess.isAir(blockPos) && blockPos.getY() > structureWorldAccess.getBottomY() + 2; blockPos = blockPos.down()) {
        }
            int length = random.nextBetween(5,10);
            blockPos = blockPos.up(random.nextInt(2));
            double angleX = random.nextFloat() * 2 * Math.PI;
            double angleY = random.nextFloat() * Math.PI / 2f;

            BlockState blockState = ModBlocks.CRYSTAL_BLOCK.getDefaultState();
        for(int i = 0; i < length; i++) {

            structureWorldAccess.setBlockState(blockPos,blockState,3);
            int x = (int)(Math.sin(angleX) + 0.5);
            int y = (int)(Math.cos(angleY) + 0.5);
            int z = (int)(Math.cos(angleX) + 0.5);

            blockPos.add(x,y,z);

            if (blockPos.getY() >= structureWorldAccess.getTopY()) break;

        }
            return true;

    }
}
