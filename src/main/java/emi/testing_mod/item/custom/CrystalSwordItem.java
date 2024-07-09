package emi.testing_mod.item.custom;

import emi.testing_mod.sound.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;

public class CrystalSwordItem extends SwordItem {

    public CrystalSwordItem(ToolMaterial toolMaterial, Item.Settings settings) {
        super(toolMaterial,20, 5,settings);
    }



    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        if(!context.getWorld().isClient()) {
            BlockPos positionClicked = context.getBlockPos();
            PlayerEntity player = context.getPlayer();

            player.getItemCooldownManager().set(this, 100);
            BlockState state = context.getWorld().getBlockState(positionClicked);
            player.sendMessage(Text.literal(state.getBlock().asItem().getName().getString()), false);

            ArmorStandEntity test = new ArmorStandEntity(context.getWorld(),positionClicked.getX() + 0.5,positionClicked.getY() + 2,positionClicked.getZ() + 0.5);
            test.equipStack(EquipmentSlot.HEAD,context.getStack());
            test.setInvisible(true);
            test.setInvulnerable(true);
            //test.setNoGravity(true);
            context.getWorld().spawnEntity(test);

            //TODO make the entity move down and stop when it hits the ground (and play a sound

            context.getWorld().playSound(null,positionClicked, ModSounds.CRYSTAL_SWORD_SUMMON, SoundCategory.PLAYERS,1f,1f);

            context.getStack().damage(10, context.getPlayer(), playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));


        }
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        stack.damage(1, attacker, playerEntity -> attacker.sendToolBreakStatus(attacker.getActiveHand()));
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 20, 2), attacker);

        if(!attacker.getWorld().isClient())
            attacker.playSound(ModSounds.CRYSTAL_SWORD_SWING,1f,1f);

        return super.postHit(stack, target, attacker);
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.testing_mod.crystal_sword.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
