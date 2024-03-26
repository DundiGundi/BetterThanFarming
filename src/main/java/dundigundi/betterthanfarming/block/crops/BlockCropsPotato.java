package dundigundi.betterthanfarming.block.crops;

import dundigundi.betterthanfarming.item.BetterThanFarmingItems;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockCropsWheat;
import net.minecraft.core.block.BlockFlower;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import turniplabs.halplibe.helper.TextureHelper;

import java.util.Random;

import static dundigundi.betterthanfarming.BetterThanFarming.MOD_ID;

public class BlockCropsPotato extends BlockFlower {
	public final int[] growthStageTextures = new int[]{
			TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "potato_growing_01.png"),
			TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "potato_growing_02.png"),
			TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "potato_growing_03.png"),
			TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "potato_growing_04.png")
	};

	public BlockCropsPotato(String key, int id) {
		super(key, id);
		this.setTicking(true);
		float f = 0.5f;
		this.setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 0.25f, 0.5f + f);
	}
	@Override
	public boolean canThisPlantGrowOnThisBlockID(int i) {
		return i == Block.farmlandDirt.id;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		float f;
		int l;
		super.updateTick(world, x, y, z, rand);
		if (world.getBlockLightValue(x, y + 1, z) >= 9 && (l = world.getBlockMetadata(x, y, z)) < 3 && rand.nextInt((int)(100.0f / (f = this.getGrowthRate(world, x, y, z)))) == 0) {
			world.setBlockMetadataWithNotify(x, y, z, ++l);
		}
	}

	public void fertilize(World world, int i, int j, int k) {
		world.setBlockMetadataWithNotify(i, j, k, 3);
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
		if (data < 0 || data > 3) {
			data = 3;
		}
		return this.growthStageTextures[data];
	}

	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
		if (meta != 3) {
			return new ItemStack[]{new ItemStack(BetterThanFarmingItems.foodPotatoRaw)};
		}
		return new ItemStack[]{new ItemStack(BetterThanFarmingItems.foodPotatoRaw, world.rand.nextInt(3) + 1), new ItemStack(BetterThanFarmingItems.foodPotatoRaw)};
	}
}
