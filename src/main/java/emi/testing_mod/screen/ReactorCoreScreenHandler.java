package emi.testing_mod.screen;

import emi.testing_mod.block.entity.ReactorCoreEntity;
import emi.testing_mod.item.ModItems;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class ReactorCoreScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final ReactorCoreEntity blockEntity;

    public ReactorCoreScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId,inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
                new ArrayPropertyDelegate(4));

    }

    public ReactorCoreScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate) {
        super(ModScreenHandlers.REACTOR_CORE_SCREEN_HANDLER, syncId);
        checkSize((Inventory) blockEntity,2);
        this.inventory = (Inventory) blockEntity;
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = (ReactorCoreEntity) blockEntity;

        int offset = 20;
        this.addSlot(new Slot(inventory,0,36,56 -offset)); //use inventory (inventory = inventory of blockEntity)
        this.addSlot(new Slot(inventory,1,48,22 -offset));
        this.addSlot(new Slot(inventory,2,80,11 - offset));
        this.addSlot(new Slot(inventory,3,113,22 -offset));
        this.addSlot(new Slot(inventory,4,125,56 -offset));
        this.addSlot(new Slot(inventory,5,113,89 -offset));
        this.addSlot(new Slot(inventory,6,80,100 -offset));
        this.addSlot(new Slot(inventory,7,48,89 -offset));
        this.addSlot(new Slot(inventory,8,80,56 -offset));


        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(arrayPropertyDelegate);
    }


    public boolean isPowered(){
        return propertyDelegate.get(0) > 0;
    }
    public boolean isHeated(){
        return propertyDelegate.get(2) > 0;
    }

    public boolean[] getInvArray(){
        boolean[] ret = new boolean[inventory.size()];

        for(int i = 0; i < ret.length; i++)
        {
            if(inventory.getStack(i).getItem() == ModItems.POLISHED_CRYSTAL_SHARD)
                ret[i] = true;

        }

        return ret;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);

        if(slot != null && slot.hasStack()){
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if(invSlot < this.inventory.size())
            {
                if(!this.insertItem(originalStack, this.inventory.size(), this.slots.size(),true))
                {
                    return ItemStack.EMPTY;
                }

            } else if(!this.insertItem(originalStack, 0,this.inventory.size(),false))
            {
                return ItemStack.EMPTY;
            }

            if(originalStack.isEmpty())
            {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    public int getScaledPower()
    {
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);
        int progressArrowSize = 79; //width in pixels of arrow

        return maxProgress !=0 && progress != 0 ? progress*progressArrowSize / maxProgress : 0;
    }

    public int getScaledHeat()
    {
        int progress = this.propertyDelegate.get(2);
        int maxProgress = this.propertyDelegate.get(3);
        int progressArrowSize = 79; //width in pixels of arrow

        return maxProgress !=0 && progress != 0 ? progress*progressArrowSize / maxProgress : 0;
    }

    private void addPlayerInventory(PlayerInventory playerInventory)
    {
        for(int i = 0; i < 3; ++i)
        {
            for(int l = 0; l < 9; ++l)
            {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l *18, 105 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory){
        for(int i = 0; i < 9; ++i){
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 163));
        }
    }
}
