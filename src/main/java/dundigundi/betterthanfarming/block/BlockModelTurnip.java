package dundigundi.betterthanfarming.block;

import dundigundi.betterthanfarming.item.BetterThanFarmingItems;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockTransparent;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import useless.dragonfly.model.block.processed.BlockCube;

import java.util.ArrayList;

import static org.apache.log4j.builders.appender.SocketAppenderBuilder.LOGGER;

public class BlockModelTurnip extends BlockTransparent {
	public useless.dragonfly.model.block.processed.BlockModel model;
	public BlockModelTurnip(String key, int id, Material material, useless.dragonfly.model.block.processed.BlockModel model) {
		super(key, id, material, true);
		this.model = model;
	}
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	@Override
	public boolean canPlaceOnSurface() {
		return true;
	}
	@Override
	public int getRenderBlockPass() {
		return 0;
	}
	@Override
	public void getCollidingBoundingBoxes(World world, int x, int y, int z, AABB aabb, ArrayList<AABB> aabbList) {
		for (int i = 0; i < model.blockCubes.length; i++) {
			BlockCube cube = model.blockCubes[i];
			// i goes from #1 -> last in json file
			if (i == 0 || i == 1){
				setBlockBounds(cube.xMin(), cube.yMin() * 0, cube.zMin(), cube.xMax(), cube.yMax() * 0, cube.zMax() ); //leaves
			}else {
				setBlockBounds(cube.xMin(), cube.yMin(), cube.zMin(), cube.xMax(), cube.yMax(), cube.zMax()); //body
			}
			super.getCollidingBoundingBoxes(world, x, y, z, aabb, aabbList);
		}
		//this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
	}
	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileentity) {
		switch (dropCause) {
			case SILK_TOUCH:
			case PICK_BLOCK:
				return new ItemStack[]{new ItemStack(this)};
			default:
				return new ItemStack[]{new ItemStack(BetterThanFarmingItems.foodTurnip, world.rand.nextInt(3) + 1)};
		}
	}
}
