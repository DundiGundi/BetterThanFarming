package dundigundi.betterthanfarming.block.entity;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import dundigundi.betterthanfarming.block.BetterThanFarmingBlocks;
import dundigundi.betterthanfarming.block.BlockCheeseMaker;
import dundigundi.betterthanfarming.block.BlockStove;
import dundigundi.betterthanfarming.misc.LookupCookingIngredients;
import dundigundi.betterthanfarming.recipe.RecipesCheeseMaker;
import net.java.games.input.Component;
import net.java.games.input.Mouse;
import net.minecraft.client.input.MouseInput;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFurnace;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileEntityStove extends TileEntity implements IInventory {
	public final List<Item> contentsToCook = new ArrayList<Item>(); //CAN'T HAVE HASHMAP, BEACUSE YOU CAN STORE MORE ITEM FROM THE SAME TYPE
	public final List<Integer> contentsToCookCurrentCookTime = new ArrayList<Integer>();
	public int maxCookTime = 200;
	protected ItemStack[] contents = new ItemStack[1];
	public int currentBurnTime = 0;
	public int maxBurnTime = 0;

	public float itemRenderOffsetX = 0.3f;
	public float itemRenderOffsetZ = 0.4f;
	public float itemRenderRelativeX = 0;
	public float itemRenderRelativeZ = 0;

	public TileEntityStove() {
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
		return "Stove";
	}

	@Override
	public void readFromNBT(CompoundTag CompoundTag) {
		super.readFromNBT(CompoundTag);

		int size = CompoundTag.getInteger("ContentsToCookSize");
		for (int i = 0; i < size; i++) {
			contentsToCook.add(Item.itemsList[CompoundTag.getInteger("ContentToCook" + i)]);
			contentsToCookCurrentCookTime.add(CompoundTag.getInteger("ContentToCookCurrentCookTime"));
		}
		ListTag listTag = CompoundTag.getList("Items");

		contents = new ItemStack[getSizeInventory()];
		for (int i = 0; i < listTag.tagCount(); i++) {
			CompoundTag compoundTag2 = (com.mojang.nbt.CompoundTag) listTag.tagAt(i);
			int slot = compoundTag2.getInteger("Slot");

			if (slot >= 0 && slot < contents.length)
				contents[slot] = ItemStack.readItemStackFromNbt(compoundTag2);
		}
		this.currentBurnTime = CompoundTag.getShort("BurnTime");
	}

	@Override
	public void writeToNBT(CompoundTag CompoundTag) {
		super.writeToNBT(CompoundTag);

		CompoundTag.putInt("ContentsToCookSize", contentsToCook.size());
		for (int i = 0; i < contentsToCook.size(); i++) {
			CompoundTag.putInt("ContentToCook" + i, contentsToCook.get(i).id);
			CompoundTag.putInt("ContentToCookCurrentCookTime", contentsToCookCurrentCookTime.get(i));
		}
		CompoundTag.putShort("BurnTime", (short)this.currentBurnTime);

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
		if (worldObj.getBlockTileEntity(x, y, z) != this) {
			return false;
		}
		return entityPlayer.distanceToSqr(x + 0.5f, y + 0.5f, z + 0.5f) <= 64;
	}

	@Override
	public void sortInventory() {

	}

	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();
	}

	public int getBurnTimeRemainingScaled(int i) {
		if (this.maxBurnTime == 0) {
			return 0;
		}
		return this.currentBurnTime * i / this.maxBurnTime;
	}

	public boolean isBurning() {
		return this.currentBurnTime > 0;
	}

	@Override
	public void tick() {
		boolean isBurnTimeHigherThan0 = this.currentBurnTime > 0;
		boolean stoveUpdated = false;
		if (this.currentBurnTime > 0) {
			--this.currentBurnTime;
		}
		if (this.worldObj != null && !this.worldObj.isClientSide) {
			if (this.worldObj.getBlockId(this.x, this.y, this.z) == BetterThanFarmingBlocks.stoveIdle.id && this.currentBurnTime == 0 && this.contents[0] != null && this.contents[0].itemID == Block.netherrack.id) {
				--this.contents[0].stackSize;
				if (this.contents[0].stackSize <= 0) {
					this.contents[0] = null;
				}
				BlockStove.updateStoveBlockState(true, this.worldObj, this.x, this.y, this.z);
				stoveUpdated = true;
			}
			if (this.currentBurnTime == 0 && this.canProduce()) {
				this.maxBurnTime = this.currentBurnTime = this.getBurnTimeFromItem(contents[0]);
				if (this.currentBurnTime > 0) {
					stoveUpdated = true;
					if (this.contents[0] != null) {
						if (this.contents[0].getItem() == Item.bucketLava) {
							this.contents[0] = new ItemStack(Item.bucket);
						} else {
							--this.contents[0].stackSize;
							if (this.contents[0].stackSize <= 0) {
								this.contents[0] = null;
							}
						}
					}
				}
			}
			if (this.isBurning() && this.canProduce()) {
				for (int i = 0; i < contentsToCookCurrentCookTime.size(); i++) {
					int temp = contentsToCookCurrentCookTime.get(i);
					contentsToCookCurrentCookTime.set(i, --temp);
					if (contentsToCookCurrentCookTime.get(i) <= 0){
						Item currentItem = contentsToCook.get(i);
						if (LookupCookingIngredients.instance.getResults(currentItem.id) != Item.foodPorkchopCooked ) {
							worldObj.dropItem(x, y + 1, z, new ItemStack(LookupCookingIngredients.instance.getResults(currentItem.id), 1));
						}else {
							worldObj.dropItem(x, y + 1, z, new ItemStack(Item.foodPorkchopCooked, 1));
						}
						contentsToCookCurrentCookTime.remove(i);
						contentsToCook.remove(i);
					}
				}
			}

			if (isBurnTimeHigherThan0 != this.currentBurnTime > 0) {
				stoveUpdated = true;
				this.updateStove();
			}
		}
		if (stoveUpdated) {
			this.onInventoryChanged();
		}
	}
	protected void updateStove() {
		BlockStove.updateStoveBlockState(this.currentBurnTime > 0, this.worldObj, this.x, this.y, this.z);
	}
	private int getBurnTimeFromItem(ItemStack itemStack) {
		if (itemStack == null) {
			return 0;
		}
		return LookupFuelFurnace.instance.getFuelYield(itemStack.getItem().id);
	}

	private boolean canProduce() {
		return !contentsToCook.isEmpty();
	}
}
