package emi.testing_mod.item.custom;

import emi.testing_mod.sound.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;

import static net.minecraft.item.BucketItem.getEmptiedStack;

public class CrystalShardItem extends Item {

    public CrystalShardItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand){

        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.NONE);

        BlockState state = world.getBlockState(blockHitResult.getBlockPos());


        if (blockHitResult.getType() == HitResult.Type.MISS) {
                return TypedActionResult.pass(itemStack);
            } else if (blockHitResult.getType() != HitResult.Type.BLOCK) {
                return TypedActionResult.pass(itemStack);
            } else {


                if (state.isOf(Blocks.GRINDSTONE) && !world.isClient()) {

                    world.playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(), ModSounds.CRYSTAL_SWORD_SWING, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
                    itemStack.decrement(1);

                    ItemStack polished_crystal = new ItemStack(Registries.ITEM.get(new Identifier("testing_mod:polished_crystal_shard")));

                    boolean wasAdded = user.getInventory().insertStack(polished_crystal);

                    if (!wasAdded) {
                        user.dropItem(polished_crystal, false);
                    }


                        return TypedActionResult.success(itemStack);
                }

                return TypedActionResult.fail(itemStack);
            }





    }
}
