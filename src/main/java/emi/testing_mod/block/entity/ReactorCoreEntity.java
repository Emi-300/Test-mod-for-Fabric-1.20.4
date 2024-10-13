package emi.testing_mod.block.entity;

import emi.testing_mod.block.ModBlocks;
import emi.testing_mod.block.custom.LaserBlock;
import emi.testing_mod.item.ModItems;
import emi.testing_mod.screen.ReactorCoreScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3i;

public class ReactorCoreEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory{

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;
    private int power = 0;
    private int maxPower = 600;
    private int maxCurrentPower = 0;
    private int heat = 0;
    private int maxHeat = 6000;
    private int numLasers = 0;

    public ReactorCoreEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REACTOR_CORE_ENTITY, pos, state);

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> ReactorCoreEntity.this.power;
                    case 1 -> ReactorCoreEntity.this.maxPower;
                    case 2 -> ReactorCoreEntity.this.heat;
                    case 3 -> ReactorCoreEntity.this.maxHeat;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> ReactorCoreEntity.this.power = value;
                    case 1 -> ReactorCoreEntity.this.maxPower = value;
                    case 2 -> ReactorCoreEntity.this.heat = value;
                    case 3 -> ReactorCoreEntity.this.maxHeat = value;
                };
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("power", power);
        nbt.putInt("heat", heat);
        nbt.putInt("numLasers",numLasers);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        power = nbt.getInt("power");
        heat = nbt.getInt("heat");
        numLasers = nbt.getInt("numLasers");
    }


    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
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
        return Text.literal("Reactor Core");
    }

    public BlockState getBlockState()
    {
        return world.getBlockState(pos);
    }


    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ReactorCoreScreenHandler(syncId,playerInventory,this,this.propertyDelegate);
    }




    //block entity logic

    public void tick(World world, BlockPos pos, BlockState state) {

        updateNumLasers();
        updateMaxPower();

        if(power < maxCurrentPower)
            power++;

        if(heat < maxHeat)
        {
            heat += numLasers * 10;
            if(heat > maxHeat)
                heat = maxHeat;
        }

        reduceHeat();

    }

    public void reduceHeat()
    {
        boolean[] invArray = getInvArray();

        int numCooling = 0;
        for(boolean i : invArray)
            if(i)
                numCooling++;

        heat -= numCooling * 7;

        if(heat<0)
            heat = 0;
    }

    public void updateMaxPower()
    {
        maxCurrentPower = 100 * numLasers;
    }

    public void updateNumLasers()
    {
        int nLas = 0;
        for(int i = -1; i <= 1; i+=2)
        {
            Vector3i direction = new Vector3i(i,0,0);
            BlockPos position = findLaser(direction);
            if(position != null && world.getBlockState(position).get(LaserBlock.POWERED))
                nLas++;

        }
        for(int i = -1; i <= 1; i+=2)
        {
            Vector3i direction = new Vector3i(0,i,0);
            BlockPos position = findLaser(direction);
            if(position != null && world.getBlockState(position).get(LaserBlock.POWERED))
                nLas++;
        }

        for(int i = -1; i <= 1; i+=2)
        {
            Vector3i direction = new Vector3i(0,0,i);
            BlockPos position = findLaser(direction);
            if(position != null && world.getBlockState(position).get(LaserBlock.POWERED))
                nLas++;
        }

        numLasers = nLas;
    }

    public BlockPos findLaser(Vector3i direction)
    {
        int length = 1;

        while(length != 50)
        {
            length++;

            BlockPos newDir = new BlockPos(this.pos.getX() + direction.x * length, this.pos.getY() + direction.y * length, this.pos.getZ() + direction.z * length);

            Block block = world.getBlockState(newDir).getBlock();

            if(block == ModBlocks.LASER_BLOCK)
            {
                return newDir;
            }
        }
        return null;
    }

    public boolean[] getInvArray(){
        boolean[] ret = new boolean[inventory.size()];

        for(int i = 0; i < ret.length; i++)
        {
            if(inventory.get(i).getItem() == ModItems.POLISHED_CRYSTAL_SHARD)
                ret[i] = true;

        }

        return ret;
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
