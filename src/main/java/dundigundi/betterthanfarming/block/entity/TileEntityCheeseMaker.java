package dundigundi.betterthanfarming.block.entity;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import dundigundi.betterthanfarming.block.BetterThanFarmingBlocks;
import dundigundi.betterthanfarming.block.BlockCheeseMaker;
import dundigundi.betterthanfarming.recipe.RecipesCheeseMaker;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;

public class TileEntityCheeseMaker extends TileEntity implements IInventory {
	private final RecipesCheeseMaker recipes = new RecipesCheeseMaker();
	protected ItemStack[] contents = new ItemStack[5];
	public int currentCheeseMakerTime = 0;
	public int currentMilkAmount = 0;
	public int maxCheeseMakerTime;
	public int maxMilkAmount;

	public TileEntityCheeseMaker() {
		this.maxMilkAmount = 4;
		this.maxCheeseMakerTime = 400;
	}

	@Override
	public int getSizeInventory() {
		return contents.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return contents[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (contents[i] != null) {
			if (contents[i].stackSize <= j) {
				ItemStack itemstack = contents[i];
				contents[i] = null;
				return itemstack;
			} else {
				ItemStack splitStack = contents[i].splitStack(j);
				if (contents[i].stackSize <= 0) {
					contents[i] = null;
				}

				return splitStack;
			}
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		contents[i] = itemStack;
		if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
			itemStack.stackSize = getInventoryStackLimit();

		onInventoryChanged();
	}

	public String getInvName() {
		return "CheeseMaker";
	}

	@Override
	public void readFromNBT(CompoundTag CompoundTag) {
		super.readFromNBT(CompoundTag);
		currentCheeseMakerTime = CompoundTag.getInteger("CheeseMakerTime");
		currentMilkAmount = CompoundTag.getInteger("MilkAmount");

		ListTag listTag = CompoundTag.getList("Items");

		contents = new ItemStack[getSizeInventory()];
		for (int i = 0; i < listTag.tagCount(); i++) {
			CompoundTag compoundTag2 = (com.mojang.nbt.CompoundTag) listTag.tagAt(i);
			int slot = compoundTag2.getInteger("Slot");

			if (slot >= 0 && slot < contents.length)
				contents[slot] = ItemStack.readItemStackFromNbt(compoundTag2);
		}
	}

	@Override
	public void writeToNBT(CompoundTag CompoundTag) {
		super.writeToNBT(CompoundTag);
		CompoundTag.putInt("CheeseMakerTime", currentCheeseMakerTime);
		CompoundTag.putInt("MilkAmount", currentMilkAmount);

		ListTag listTag = new ListTag();
		for (int i = 0; i < contents.length; i++) {
			if (contents[i] != null) {
				CompoundTag compoundTag2 = new CompoundTag();

				compoundTag2.putInt("Slot", i);
				contents[i].writeToNBT(compoundTag2);
				listTag.addTag(compoundTag2);
			}
		}
		CompoundTag.put("Items", listTag);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		if (worldObj.getBlockTileEntity(x, y, z) != this)
			return false;

		return entityPlayer.distanceToSqr(x + 0.5f, y + 0.5f, z + 0.5f) <= 64;
	}

	@Override
	public void sortInventory() {

	}

	@Override
	public void onInventoryChanged() {
		if (contents[0] != null){
			if (contents[0].getItem() == Item.bucketMilk && currentMilkAmount < maxMilkAmount){
				++currentMilkAmount;
				contents[0] = new ItemStack(Item.bucket);
			}
		}
		super.onInventoryChanged();
	}

	@Override
	public void tick() {
		super.tick();
		if (currentCheeseMakerTime > maxCheeseMakerTime)
			currentCheeseMakerTime = 0;

		boolean cheeseMakerUpdated = false;
		boolean makingCheese = false;

		if (this.worldObj != null && !this.worldObj.isClientSide) {
			if (worldObj.getBlockId(x, y, z) == BetterThanFarmingBlocks.cheeseMaker.id &&
					currentCheeseMakerTime == 0 &&
					contents[2] == null) {
				BlockCheeseMaker.updateBlockState(true, worldObj, x, y, z);
				cheeseMakerUpdated = true;
			}

			if (canProduce()) {
				++currentCheeseMakerTime;
				makingCheese = true;

				if (currentCheeseMakerTime == maxCheeseMakerTime) {
					currentCheeseMakerTime = 0;
					produceItem();
					makingCheese = false;
					cheeseMakerUpdated = true;
				}
			} else {
				currentCheeseMakerTime = 0;
				makingCheese = false;
			}
		}

		if (cheeseMakerUpdated)
			onInventoryChanged();

		if (makingCheese) {
			worldObj.notifyBlockChange(x, y, z, BetterThanFarmingBlocks.cheeseMaker.id);
		}
	}

	private boolean isProducible(ItemStack itemStack) {
		return recipes.getRecipeList().containsKey(itemStack.getItem().id);
	}

	private boolean canProduce() {
		if (contents[1] != null && contents[1].getItem() != null && contents[2] != null && contents[2].getItem() != null && contents[3] != null && contents[3].getItem() != null && currentMilkAmount != 0) {
			if (isProducible(contents[2])) {
				ItemStack resultStack = recipes.getRecipeResult(contents[2].getItem().id);

				return contents[4] == null || contents[4].getItem() == resultStack.getItem() &&
						contents[4].stackSize + resultStack.stackSize <= resultStack.getMaxStackSize();
			}
		}
		return false;
	}

	private void produceItem() {
		if (canProduce()) {
			ItemStack itemStack = recipes.getRecipeResult(contents[2].getItem().id);

			if (contents[4] == null)
				contents[4] = itemStack.copy();
			else
			if (contents[4].itemID == itemStack.itemID)
				contents[4].stackSize += itemStack.stackSize;

			--contents[1].stackSize;
			--contents[2].stackSize;
			--contents[3].stackSize;
			--currentMilkAmount;

			if (contents[1].stackSize <= 0)
				contents[1] = null;
			if (contents[2].stackSize <= 0)
				contents[2] = null;
			if (contents[3].stackSize <= 0)
				contents[3] = null;
		}
	}

}
