package emi.testing_mod.block.custom;

import com.mojang.serialization.MapCodec;
import emi.testing_mod.Testing_mod;
import emi.testing_mod.block.entity.LaserBlockEntity;
import emi.testing_mod.block.entity.ModBlockEntities;
import emi.testing_mod.particle.ModParticles;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LaserBlock extends BlockWithEntity implements BlockEntityProvider{

    public static final MapCodec<LaserBlock> CODEC = createCodec(LaserBlock::new);
    public static final BooleanProperty POWERED;
    public static final DirectionProperty FACING;

    private static final VoxelShape BASE_SHAPE;

    private static final VoxelShape X_STEP_SHAPE;
    private static final VoxelShape Y_STEP_SHAPE;
    private static final VoxelShape Z_STEP_SHAPE;

    private static final VoxelShape X_STEM_SHAPE;
    private static final VoxelShape Y_STEM_SHAPE;
    private static final VoxelShape Z_STEM_SHAPE;

    private static final VoxelShape EAST_STEM_SHAPE;
    private static final VoxelShape SOUTH_STEM_SHAPE;
    private static final VoxelShape TOP_STEM_SHAPE;

    private static final VoxelShape X_LENS_SHAPE;
    private static final VoxelShape Y_LENS_SHAPE;
    private static final VoxelShape Z_LENS_SHAPE;

    private static final VoxelShape EAST_LENS_SHAPE;
    private static final VoxelShape SOUTH_LENS_SHAPE;
    private static final VoxelShape TOP_LENS_SHAPE;

    private static final VoxelShape NORTH_SHAPE;
    private static final VoxelShape EAST_SHAPE;
    private static final VoxelShape SOUTH_SHAPE;
    private static final VoxelShape WEST_SHAPE;
    private static final VoxelShape TOP_SHAPE;
    private static final VoxelShape BOTTOM_SHAPE;





    public MapCodec<LaserBlock> getCodec() {
        return CODEC;
    }


    public LaserBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.DOWN)).with(POWERED, false));
    }

    //shape

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch ((state.get(FACING))) {
            case NORTH:
                return NORTH_SHAPE;
            case EAST:
                return EAST_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case WEST:
                return WEST_SHAPE;
            case UP:
                return TOP_SHAPE;
            case DOWN:
            default:
                return BOTTOM_SHAPE;
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    //particle system

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {

        if(state.get(POWERED)) {

            Direction direction = (Direction) state.get(FACING);
            double d = (double) pos.getX();// + 0.55 - (double)(random.nextFloat() * 0.1F);
            double e = (double) pos.getY();// + 0.55 - (double)(random.nextFloat() * 0.1F);
            double f = (double) pos.getZ();// + 0.55 - (double)(random.nextFloat() * 0.1F);
            double g = (double) (0.4F - (random.nextFloat() + random.nextFloat()) * 0.4F);


           // world.addParticle(ModParticles.LASER_PARTICLE, d + (double) direction.getOffsetX() * -2 + 0.5, e + (double) direction.getOffsetY() * -2 + 0.5, f + (double) direction.getOffsetZ() * -2 + 0.5, 0, 0, 0);
           // world.addParticle(ModParticles.LASER_PARTICLE, d + (double) direction.getOffsetX() * -2 + 0.5, e + (double) direction.getOffsetY() * -2 + 0.5, f + (double) direction.getOffsetZ() * -2 + 0.5, random.nextGaussian() * 0.05, random.nextGaussian() * 0.05, random.nextGaussian() * 0.05);
        }

    }

    //Block entity

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LaserBlockEntity(pos,state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof LaserBlockEntity)
            {
                ItemScatterer.spawn(world,pos,(LaserBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient()) {
            NamedScreenHandlerFactory screenHandlerFactory = ((LaserBlockEntity) world.getBlockEntity(pos));

            if(screenHandlerFactory != null)
            {
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.LASER_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }

    //directions

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite().getOpposite());
    }


    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient) {
            boolean bl = (Boolean)state.get(POWERED);
            if (bl != world.isReceivingRedstonePower(pos)) {
                if (bl) {
                    world.scheduleBlockTick(pos, this, 4);
                } else {
                    world.setBlockState(pos, (BlockState)state.cycle(POWERED), 2);
                }
            }

        }
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if ((Boolean)state.get(POWERED) && !world.isReceivingRedstonePower(pos)) {
            world.setBlockState(pos, (BlockState)state.cycle(POWERED), 2);
        }

    }
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, POWERED});
    }




    static {
        FACING = FacingBlock.FACING;
        POWERED = Properties.POWERED;

        BASE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);

        X_STEP_SHAPE = Block.createCuboidShape(-8.0, 2.0, 2.0, 24.0, 14.0, 14.0);
        Y_STEP_SHAPE = Block.createCuboidShape(2.0, -8.0, 2.0, 14.0, 24.0, 14.0);
        Z_STEP_SHAPE = Block.createCuboidShape(2.0, 2.0, -8.0, 14.0, 14.0, 24.0);

        X_STEM_SHAPE = Block.createCuboidShape(-16.0, 0.0, 0.0, -7.0, 16.0, 16.0);
        Y_STEM_SHAPE = Block.createCuboidShape(0.0, -16.0, 0.0, 16.0, -7.0, 16.0);
        Z_STEM_SHAPE = Block.createCuboidShape(0, 0, -16, 16, 16, -7);

        EAST_STEM_SHAPE = Block.createCuboidShape(23.0, 0.0, 0.0, 32, 16.0, 16.0);
        TOP_STEM_SHAPE = Block.createCuboidShape(-2.0, 23.0, 0.0, 16.0, 32, 16.0);
        SOUTH_STEM_SHAPE = Block.createCuboidShape(0, 0, 23, 16, 16, 32);

        X_LENS_SHAPE = Block.createCuboidShape(24.0, 4.0, 4.0, 32.0, 12.0, 12.0);
        Y_LENS_SHAPE = Block.createCuboidShape(4.0, 24.0, 4.0, 12.0, 32.0, 12.0);
        Z_LENS_SHAPE = Block.createCuboidShape(4.0, 4.0, 24.0, 12.0, 12.0, 32.0);

        EAST_LENS_SHAPE = Block.createCuboidShape(-16.0, 4.0, 4.0, -8.0, 12.0, 12.0);
        TOP_LENS_SHAPE = Block.createCuboidShape(4.0, -16.0, 4.0, 12.0, -8.0, 12.0);
        SOUTH_LENS_SHAPE = Block.createCuboidShape(4.0, 4.0, -16.0, 12.0, 12.0, -8.0);

        NORTH_SHAPE = VoxelShapes.union(BASE_SHAPE, new VoxelShape[]{Z_STEP_SHAPE,Z_STEM_SHAPE,Z_LENS_SHAPE});
        EAST_SHAPE = VoxelShapes.union(BASE_SHAPE, new VoxelShape[]{X_STEP_SHAPE,EAST_STEM_SHAPE, EAST_LENS_SHAPE});
        SOUTH_SHAPE = VoxelShapes.union(BASE_SHAPE, new VoxelShape[]{Z_STEP_SHAPE,SOUTH_STEM_SHAPE,SOUTH_LENS_SHAPE});
        WEST_SHAPE = VoxelShapes.union(BASE_SHAPE, new VoxelShape[]{X_STEP_SHAPE,X_STEM_SHAPE, X_LENS_SHAPE});
        TOP_SHAPE = VoxelShapes.union(BASE_SHAPE, new VoxelShape[]{Y_STEP_SHAPE,TOP_STEM_SHAPE, TOP_LENS_SHAPE});
        BOTTOM_SHAPE = VoxelShapes.union(BASE_SHAPE, new VoxelShape[]{Y_STEP_SHAPE,Y_STEM_SHAPE, Y_LENS_SHAPE});
    }



}
