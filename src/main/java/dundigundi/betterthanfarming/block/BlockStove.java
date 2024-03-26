package dundigundi.betterthanfarming.block;


import dundigundi.betterthanfarming.block.entity.TileEntityStove;
import dundigundi.betterthanfarming.misc.LookupCookingIngredients;
import net.minecraft.core.block.BlockTileEntityRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.lwjgl.input.Keyboard;
import sunsetsatellite.catalyst.Catalyst;

import java.util.List;
import java.util.Random;

public class BlockStove extends BlockTileEntityRotatable {
	protected Random stoveRand = new Random();
	protected final boolean isActive;
	protected static boolean keepStoveInventory = false;

	public BlockStove(String key, int id, boolean flag) {
		super(key, id, Material.stone);
		this.isActive = flag;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		this.setDefaultDirection(world, x, y, z);
	}

	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
		switch (dropCause) {
			case PICK_BLOCK:
			case EXPLOSION:
			case PROPER_TOOL:
			case SILK_TOUCH: {
				return new ItemStack[]{new ItemStack(BetterThanFarmingBlocks.stoveIdle)};
			}
		}
		return null;
	}

	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		if (!this.isActive) {
			return;
		}
		int l = world.getBlockMetadata(x, y, z);
		float f = (float)x + 0.5f;
		float f1 = (float)y + 0.0f + rand.nextFloat() * 6.0f / 16.0f;
		float f2 = (float)z + 0.5f;
		float f3 = 0.52f;
		float f4 = rand.nextFloat() * 0.6f - 0.3f;
		if (l == 4) {
			world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0, 0.0, 0.0);
			world.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0, 0.0, 0.0);
		} else if (l == 5) {
			world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0, 0.0, 0.0);
			world.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0, 0.0, 0.0);
		} else if (l == 2) {
			world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0, 0.0, 0.0);
			world.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0, 0.0, 0.0);
		} else if (l == 3) {
			world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0, 0.0, 0.0);
			world.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0, 0.0, 0.0);
		}

		TileEntityStove tileEntity = (TileEntityStove) world.getBlockTileEntity(x, y, z);
		float offsetX = tileEntity.itemRenderOffsetX;
		float offsetZ = tileEntity.itemRenderOffsetZ;
		float relativeX = tileEntity.itemRenderRelativeX;
		float relativeZ = tileEntity.itemRenderRelativeZ;
		for (int i = 0; i < tileEntity.contentsToCook.size(); i++){
			if (relativeX < offsetX * 3) {
				world.spawnParticle("smoke", x + 0.2f + relativeX, y + 1.2f, z + 0.24f + relativeZ, 0, 0, 0);
				relativeX += offsetX;
				if (relativeX == offsetX * 3) {
					relativeX = 0;
					if (relativeZ < offsetZ * 2) {
						relativeZ += offsetZ;
					}
				}
			}
		}
	}

	private boolean isIngredient(Item item){
		return LookupCookingIngredients.instance.getIngredientList().containsKey(item.id);
	}
	@Override
	public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
		if (!world.isClientSide) {
			TileEntityStove tileEntityStove = (TileEntityStove) world.getBlockTileEntity(x, y, z);

			if (tileEntityStove == null)
				return false;

			boolean ctrlPressed = Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
			if (ctrlPressed){
				Item heldItem = null;
				if (player.getHeldItem() != null){
					heldItem = player.getHeldItem().getItem();
				}
				if(heldItem != null && isIngredient(heldItem) && tileEntityStove.contentsToCook.size() < 6){
					tileEntityStove.contentsToCook.add(heldItem);
					tileEntityStove.contentsToCookCurrentCookTime.add(tileEntityStove.maxCookTime);
					player.getHeldItem().consumeItem(player);
				}
			}else {
				Catalyst.displayGui(player, tileEntityStove, tileEntityStove.getInvName());
			}
		}
		return true;
	}

	public static void updateStoveBlockState(boolean lit, World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		TileEntity tileentity = world.getBlockTileEntity(x, y, z);
		if (tileentity == null) {
			String msg = "Stove is missing Tile Entity at x: " + x + " y: " + y + " z: " + z + ", block will be removed!";
			world.setBlockWithNotify(x, y, z, 0);
			System.out.println(msg);
			return;
		}
		keepStoveInventory = true;
		if (lit) {
			world.setBlockWithNotify(x, y, z, BetterThanFarmingBlocks.stoveActive.id);
		} else {
			world.setBlockWithNotify(x, y, z, BetterThanFarmingBlocks.stoveIdle.id);
		}
		keepStoveInventory = false;
		world.setBlockMetadataWithNotify(x, y, z, meta);
		tileentity.validate();
		world.setBlockTileEntity(x, y, z, tileentity);
	}

	@Override
	protected TileEntity getNewBlockEntity() {
		return new TileEntityStove();
	}

	@Override
	public void onBlockRemoved(World world, int x, int y, int z, int data) {
		if (!keepStoveInventory) {
			TileEntityStove tileEntityStove = (TileEntityStove) world.getBlockTileEntity(x, y, z);
			for (int l = 0; l < tileEntityStove.getSizeInventory(); ++l) {
				ItemStack itemstack = tileEntityStove.getStackInSlot(l);
				if (itemstack == null) continue;
				float f = this.stoveRand.nextFloat() * 0.8f + 0.1f;
				float f1 = this.stoveRand.nextFloat() * 0.8f + 0.1f;
				float f2 = this.stoveRand.nextFloat() * 0.8f + 0.1f;
				while (itemstack.stackSize > 0) {
					int i1 = this.stoveRand.nextInt(21) + 10;
					if (i1 > itemstack.stackSize) {
						i1 = itemstack.stackSize;
					}
					itemstack.stackSize -= i1;
					EntityItem entityitem = new EntityItem(world, (float)x + f, (float)y + f1, (float)z + f2, new ItemStack(itemstack.itemID, i1, itemstack.getMetadata()));
					float f3 = 0.05f;
					entityitem.xd = (float)this.stoveRand.nextGaussian() * f3;
					entityitem.yd = (float)this.stoveRand.nextGaussian() * f3 + 0.2f;
					entityitem.zd = (float)this.stoveRand.nextGaussian() * f3;
					world.entityJoinedWorld(entityitem);
				}
			}
			List<Item> contents = tileEntityStove.contentsToCook;
			for (int i = 0; i < contents.size(); i++) {
				ItemStack itemstack = new ItemStack(contents.get(i));
				if (itemstack == null) continue;
				float f = this.stoveRand.nextFloat() * 0.8f + 0.1f;
				float f1 = this.stoveRand.nextFloat() * 0.8f + 0.1f;
				float f2 = this.stoveRand.nextFloat() * 0.8f + 0.1f;
				while (itemstack.stackSize > 0) {
					int i1 = this.stoveRand.nextInt(21) + 10;
					if (i1 > itemstack.stackSize) {
						i1 = itemstack.stackSize;
					}
					itemstack.stackSize -= i1;
					EntityItem entityitem = new EntityItem(world, (float)x + f, (float)y + f1, (float)z + f2, new ItemStack(itemstack.itemID, i1, itemstack.getMetadata()));
					float f3 = 0.05f;
					entityitem.xd = (float)this.stoveRand.nextGaussian() * f3;
					entityitem.yd = (float)this.stoveRand.nextGaussian() * f3 + 0.2f;
					entityitem.zd = (float)this.stoveRand.nextGaussian() * f3;
					world.entityJoinedWorld(entityitem);
				}
			}
		}
		super.onBlockRemoved(world, x, y, z, data);
	}
}
