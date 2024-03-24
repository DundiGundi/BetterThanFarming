package dundigundi.betterthanfarming.block;

import dundigundi.betterthanfarming.BetterThanFarming;
import dundigundi.betterthanfarming.block.entity.TileEntityCheeseMaker;
import net.minecraft.core.block.BlockTileEntity;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import sunsetsatellite.catalyst.Catalyst;

public class BlockCheeseMaker extends BlockTileEntity {

	private boolean keepInventory = false;
	private final String MOD_ID = BetterThanFarming.MOD_ID;

	public BlockCheeseMaker(String key, int id, Material material) {
		super(key, id, material);
		setupInstance(this);
	}

	@Override
	protected TileEntity getNewBlockEntity() {
		return new TileEntityCheeseMaker();
	}

	@Override
	public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
		if (!world.isClientSide) {
			TileEntityCheeseMaker tileEntity = (TileEntityCheeseMaker) world.getBlockTileEntity(x, y, z);

			if (tileEntity == null)
				return false;
			Catalyst.displayGui(player, tileEntity, tileEntity.getInvName());
		}
		return true;
	}

	private void dropContents(World world, int x, int y, int z) {
		TileEntityCheeseMaker tileEntity = (TileEntityCheeseMaker) world.getBlockTileEntity(x, y, z);
		if (tileEntity == null)
			System.out.println("Can't drop inventory at X: " + x + " Y: " + y + " Z: " + z + " because TileEntity is null");
		else {
			for (int i = 0; i < tileEntity.getSizeInventory(); ++i) {
				ItemStack itemStack = tileEntity.getStackInSlot(i);
				if (itemStack != null) {
					EntityItem item = world.dropItem(x, y, z, itemStack);
					item.xd *= 0.5;
					item.yd *= 0.5;
					item.zd *= 0.5;
					item.delayBeforeCanPickup = 0;
				}
			}
		}
	}

	@Override
	public void onBlockRemoved(World world, int x, int y, int z, int meta) {
		dropContents(world, x, y, z);
		super.onBlockRemoved(world, x, y, z, meta);
	}

	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
		return dropCause != EnumDropCause.PICK_BLOCK && meta != 0 ? null : new ItemStack[]{new ItemStack(BetterThanFarmingBlocks.cheeseMaker)};
	}

	// Static Methods

	private static BlockCheeseMaker instance = null;

	private static void setupInstance(BlockCheeseMaker machine) {
		instance = machine;
	}

	private static BlockCheeseMaker getInstance() {
		if (instance == null)
			throw new NullPointerException("Instance of BlockCheeseMaker hasn't been setup!");
		return instance;
	}

	public static void updateBlockState(boolean active, World world, int x, int y, int z) {
		int metadata = world.getBlockMetadata(x, y, z);
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity == null)
			world.setBlockWithNotify(x, y, z, 0);
		else {
			getInstance().keepInventory = true;
			if (active)
				world.setBlockMetadataWithNotify(x, y, z, 1);
			if (!active)
				world.setBlockMetadataWithNotify(x, y, z, 0);

			getInstance().keepInventory = false;
			world.setBlockMetadataWithNotify(x, y, z, metadata);
			tileEntity.validate();
			world.setBlockTileEntity(x, y, z, tileEntity);
		}
	}
}
