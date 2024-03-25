package dundigundi.betterthanfarming.world;

import dundigundi.betterthanfarming.block.BetterThanFarmingBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureMelon extends WorldFeature {
	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		for (int l = 0; l < 64; ++l) {
			int k1;
			int j1;
			int i1 = x + random.nextInt(8) - random.nextInt(8);
			if (!world.isAirBlock(i1, j1 = y + random.nextInt(4) - random.nextInt(4), k1 = z + random.nextInt(8) - random.nextInt(8)) || world.getBlockId(i1, j1 - 1, k1) != Block.grass.id || !BetterThanFarmingBlocks.blockWatermelon.canPlaceBlockAt(world, i1, j1, k1)) continue;
			world.setBlockWithNotify(i1, j1, k1, BetterThanFarmingBlocks.blockWatermelon.id);
		}
		return true;
	}
}
