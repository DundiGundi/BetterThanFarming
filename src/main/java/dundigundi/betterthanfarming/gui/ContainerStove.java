package dundigundi.betterthanfarming.gui;

import dundigundi.betterthanfarming.block.entity.TileEntityStove;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.crafting.ICrafting;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.List;

public class ContainerStove extends Container {
	 private int currentBurnTime;
	InventoryPlayer inventory;
	TileEntityStove tileEntity;

	public ContainerStove(InventoryPlayer inventory, TileEntityStove tileEntity) {
		this.inventory = inventory;
		this.tileEntity = tileEntity;
		this.addSlot(new Slot(tileEntity, 0, 80, 41)); //fuel slot

		for(int xSlot = 0; xSlot < 3; ++xSlot)
			for (int ySlot = 0; ySlot < 9; ++ySlot)
				this.addSlot(new Slot(inventory, ySlot + xSlot * 9 + 9, 8 + ySlot * 18, 84 + xSlot * 18));

		for(int hotbar = 0; hotbar < 9; ++hotbar)
			this.addSlot(new Slot(inventory, hotbar, 8 + hotbar * 18, 142));
	}



	@Override
	public void updateInventory() {
		super.updateInventory();

		for(ICrafting crafter : this.crafters) {

			if (this.currentBurnTime != tileEntity.currentBurnTime)
				crafter.updateCraftingInventoryInfo(this, 0, tileEntity.currentBurnTime);
		}
		this.currentBurnTime = this.tileEntity.currentBurnTime;
	}

	@Override
	public void updateClientProgressBar(int id, int value) {
		if (id == 0) {
			this.tileEntity.currentBurnTime = value;
		}
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
		return this.tileEntity.canInteractWith(entityPlayer);
	}

	@Override
	public List<Integer> getMoveSlots(InventoryAction action, Slot slot, int target, EntityPlayer player) {
		return null;
	}

	@Override
	public List<Integer> getTargetSlots(InventoryAction action, Slot slot, int target, EntityPlayer player) {
		return null;
	}
}
