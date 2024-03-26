package dundigundi.betterthanfarming.block.crops;

import dundigundi.betterthanfarming.block.BetterThanFarmingBlocks;
import dundigundi.betterthanfarming.item.BetterThanFarmingItems;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockCropsPumpkin;
import net.minecraft.core.block.BlockFlower;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import turniplabs.halplibe.helper.TextureHelper;

import java.util.Random;

import static dundigundi.betterthanfarming.BetterThanFarming.MOD_ID;

public class BlockCropsWatermelon extends BlockFlower {
	public static final int[] GROWTH_STAGE_TEXTURES_TOP = new int[]{
			TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "watermelon_growing_top_01.png"),
			TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "watermelon_growing_top_02.png"),
			TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "watermelon_growing_top_03.png"),
			TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "watermelon_growing_top_04.png")
	};
	public static final int[] GROWTH_STAGE_TEXTURES_SIDE = new int[]{
			TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "watermelon_growing_side_01.png"),
			TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "watermelon_growing_side_02.png"),
			TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "watermelon_growing_side_03.png"),
			TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "watermelon_growing_side_04.png")
	};
	public BlockCropsWatermelon(String key, int id) {
		super(key, id);
	}

	@Override
	public void setBlockBoundsBasedOnState(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		float onePix = 0.0625f;
		float size = 0.0f;
		if (meta == 0) {
			size = 6.0f * onePix;
		} else if (meta == 1) {
			size = 8.0f * onePix;
		} else if (meta == 2) {
			size = 10.0f * onePix;
		} else if (meta == 3) {
			size = 12.0f * onePix;
		} else if (meta == 4) {
			size = 14.0f * onePix;
		}
		this.setBlockBounds(0.5f - size / 2.0f, 0.0f, 0.5f - size / 2.0f, 0.5f + size / 2.0f, size, 0.5f + size / 2.0f);
	}

	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int i) {
		return i == Block.farmlandDirt.id;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		float f;
		int meta;
		super.updateTick(world, x, y, z, rand);
		if (world.getBlockLightValue(x, y + 1, z) >= 9 && (meta = world.getBlockMetadata(x, y, z)) < 6 && rand.nextInt((int)(100.0f / (f = this.getGrowthRate(world, x, y, z)))) == 0) {
			if (++meta == 5) {
				world.setBlockAndMetadataWithNotify(x, y, z, BetterThanFarmingBlocks.blockWatermelon.id, 0);
			} else {
				world.setBlockMetadataWithNotify(x, y, z, meta);
			}
		}
	}

	public void fertilize(World world, int x, int y, int z) {
		world.setBlockWithNotify(x, y, z, BetterThanFarmingBlocks.blockWatermelon.id);
	}

	private float getGrowthRate(World world, int x, int y, int z) {
		float growthRate = 1.0f;
		int idNegZ = world.getBlockId(x, y, z - 1);
		int idPosZ = world.getBlockId(x, y, z + 1);
		int idNegX = world.getBlockId(x - 1, y, z);
		int idPosX = world.getBlockId(x + 1, y, z);
		int idNegXNegZ = world.getBlockId(x - 1, y, z - 1);
		int idPosXNegZ = world.getBlockId(x + 1, y, z - 1);
		int idPosXPosZ = world.getBlockId(x + 1, y, z + 1);
		int idNegXPosZ = world.getBlockId(x - 1, y, z + 1);
		boolean xNeighbor = idNegX == this.id || idPosX == this.id;
		boolean zNeighbor = idNegZ == this.id || idPosZ == this.id;
		boolean diagNeighbor = idNegXNegZ == this.id || idPosXNegZ == this.id || idPosXPosZ == this.id || idNegXPosZ == this.id;
		for (int dx = x - 1; dx <= x + 1; ++dx) {
			for (int dz = z - 1; dz <= z + 1; ++dz) {
				int id = world.getBlockId(dx, y - 1, dz);
				float growthRateMod = 0.0f;
				if (id == Block.farmlandDirt.id) {
					growthRateMod = 1.0f;
					if (world.getBlockMetadata(dx, y - 1, dz) > 0) {
						growthRateMod = 3.0f;
					}
				}
				if (dx != x || dz != z) {
					growthRateMod /= 4.0f;
				}
				growthRate += growthRateMod;
			}
		}
		if (diagNeighbor || xNeighbor && zNeighbor) {
			growthRate /= 2.0f;
		}
		if (world.seasonManager.getCurrentSeason() != null) {
			growthRate *= world.seasonManager.getCurrentSeason().cropGrowthFactor;
		}
		return growthRate;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(Side side, int data) {
		if (data < 1 || data > 4) {
			data = 1;
		}
		if (side == Side.TOP || side == Side.BOTTOM) {
			return GROWTH_STAGE_TEXTURES_TOP[data - 1];
		}
		return GROWTH_STAGE_TEXTURES_SIDE[data - 1];
	}

	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
		return new ItemStack[]{new ItemStack(BetterThanFarmingItems.seedsWatermelon, 1)};
	}

	@Override
	public AABB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		this.setBlockBoundsBasedOnState(world, x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 0) {
			return null;
		}
		return AABB.getBoundingBoxFromPool((double)x + this.minX, (double)y + this.minY, (double)z + this.minZ, (double)x + this.maxX, (double)y + this.maxY, (double)z + this.maxZ);
	}
}
