package emi.testing_mod.block.entity;

import emi.testing_mod.Testing_mod;
import emi.testing_mod.item.ModItems;
import emi.testing_mod.recipe.LaserRecipe;
import emi.testing_mod.screen.LaserBlockScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3i;

import java.util.Optional;

public class LaserBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 100;

    public int timer = 0;
    public float laser_length;

    public LaserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LASER_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> LaserBlockEntity.this.progress;
                    case 1 -> LaserBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> LaserBlockEntity.this.progress = value;
                    case 1 -> LaserBlockEntity.this.maxProgress = value;
                };
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }


    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("progress", progress);
        nbt.putInt("orb timer", timer);
        nbt.putFloat("laser length",laser_length);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("progress");
        timer = nbt.getInt("orb timer");
        laser_length = nbt.getFloat("laser length");
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    public ItemStack getRenderStack() {
        return this.getStack(INPUT_SLOT);
    }

    @Override
    public void markDirty() {
        world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        super.markDirty();
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);

    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Laser Block Interface");
    }


    public BlockState getBlockState()
    {
        return world.getBlockState(pos);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new LaserBlockScreenHandler(syncId,playerInventory,this,this.propertyDelegate);
    }

    //block entity logic

    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient())
            return;

        if(isOutputSlotReceivable()){
            if(this.hasRecipe()){
                this.increaseCraftProgress();
                markDirty(world,pos,state);

                if(hasCraftingFinished()){
                    this.craftItem();
                    this.resetProgress();
                }
            } else {
                this.resetProgress();
            }
        } else {
            this.resetProgress();
            markDirty(world,pos,state);
        }

        if(state.get(Properties.POWERED) && timer < 15)
        {

            timer++;
            markDirty(world,pos,state);
            world.updateListeners(pos, state, state, 0);

        } else if(timer > 0)
        {
            timer--;
            markDirty(world,pos,state);
            world.updateListeners(pos, state, state, 0);
        }

        if(timer > 13)
        {
            laser_length = getLaserLength();
        } else if(laser_length != 2){
            laser_length = 2.6f;
        }


    }

    public int getTimer(){
        return this.timer;
    }


    public float getLaserLength()
    {
        int length = 1;
        boolean foundBlock = false;
        Vector3i direction = new Vector3i(0,0,0);

        switch (getBlockState().get(Properties.FACING))
        {
            case UP:
                direction.y--;
                break;
            case DOWN:
                direction.y++;
                break;
            case EAST:
                direction.x--;
                break;
            case WEST:
                direction.x++;
                break;
            case NORTH:
                direction.z++;
                break;
            case SOUTH:
                direction.z--;
                break;
        }

        while(!foundBlock)
        {
            length++;

            BlockPos newDir = new BlockPos(this.pos.getX() + direction.x * length, this.pos.getY() + direction.y * length, this.pos.getZ() + direction.z * length);

            Block block = world.getBlockState(newDir).getBlock();

            if(block != Blocks.AIR && !block.isTransparent(world.getBlockState(newDir), world,newDir))
            {
                foundBlock = true;
            }

            if(length > 50)
            {
                foundBlock = true;
            }
        }

        return length;
    }

    private void resetProgress() { this.progress = 0;}

    private void craftItem() {

        Optional<RecipeEntry<LaserRecipe>> recipe = getCurrentRecipe();

        this.removeStack(INPUT_SLOT,1);

        this.setStack(OUTPUT_SLOT, new ItemStack(recipe.get().value().getResult(null).getItem(), getStack(OUTPUT_SLOT).getCount() + recipe.get().value().getResult(null).getCount()));

    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftProgress() { progress++; }

    private boolean hasRecipe() {

        Optional<RecipeEntry<LaserRecipe>> recipe = getCurrentRecipe();

        return  recipe.isPresent() && canInsertItemIntoOutput(recipe.get().value().getResult(null).getItem())
                && canInsertAmountIntoOutput(recipe.get().value().getResult(null));
    }

    private Optional<RecipeEntry<LaserRecipe>> getCurrentRecipe() {
        SimpleInventory inv = new SimpleInventory(this.size());
        for(int i = 0; i < this.size(); i++)
        {
            inv.setStack(i,this.getStack(i));
        }
        return getWorld().getRecipeManager().getFirstMatch(LaserRecipe.Type.INSTANCE, inv, getWorld());
    }

    private boolean canInsertItemIntoOutput(Item item) {
        return this.getStack(OUTPUT_SLOT).getItem() == item || this.getStack(OUTPUT_SLOT).isEmpty();
    }

    private boolean canInsertAmountIntoOutput(ItemStack result) {
        return this.getStack(OUTPUT_SLOT).getCount() + result.getCount() <= getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean isOutputSlotReceivable() {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }



    //client and server sync

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }


}
