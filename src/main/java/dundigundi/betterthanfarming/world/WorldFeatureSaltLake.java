package dundigundi.betterthanfarming.world;

import dundigundi.betterthanfarming.block.BetterThanFarmingBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeatureSaltLake extends WorldFeature {
	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		int dy;
		int dx;
		Block salt = BetterThanFarmingBlocks.blockSalt;
		x -= 8;
		z -= 8;
		while (y > 0 && world.isAirBlock(x, y, z)) {
			--y;
		}
		y -= 4;
		boolean[] shouldPlaceSalt = new boolean[2048];
		int l = random.nextInt(4) + 4;
		int rarity = random.nextInt(9);
		if (rarity == 0){
			for (int i = 0; i < l; ++i) {
				double d = random.nextDouble() * 6.0 + 3.0;
				double d1 = random.nextDouble() * 4.0 + 2.0;
				double d2 = random.nextDouble() * 6.0 + 3.0;
				double d3 = random.nextDouble() * (16.0 - d - 2.0) + 1.0 + d / 2.0;
				double d4 = random.nextDouble() * (8.0 - d1 - 4.0) + 2.0 + d1 / 2.0;
				double d5 = random.nextDouble() * (16.0 - d2 - 2.0) + 1.0 + d2 / 2.0;
				for (int dx2 = 1; dx2 < 15; ++dx2) {
					for (int dz = 1; dz < 15; ++dz) {
						for (int dy2 = 1; dy2 < 7; ++dy2) {
							double d6 = ((double)dx2 - d3) / (d / 2.0);
							double d7 = ((double)dy2 - d4) / (d1 / 2.0);
							double d8 = ((double)dz - d5) / (d2 / 2.0);
							double d9 = d6 * d6 + d7 * d7 + d8 * d8;
							if (!(d9 < 1.0)) continue;
							shouldPlaceSalt[(dx2 * 16 + dz) * 8 + dy2] = true;
						}
					}
				}
			}
			for (dx = 0; dx < 16; ++dx) {
				for (int dz = 0; dz < 16; ++dz) {
					for (dy = 0; dy < 8; ++dy) {
						boolean flag;
						boolean bl = flag = !shouldPlaceSalt[(dx * 16 + dz) * 8 + dy] && (dx < 15 && shouldPlaceSalt[((dx + 1) * 16 + dz) * 8 + dy] || dx > 0 && shouldPlaceSalt[((dx - 1) * 16 + dz) * 8 + dy] || dz < 15 && shouldPlaceSalt[(dx * 16 + (dz + 1)) * 8 + dy] || dz > 0 && shouldPlaceSalt[(dx * 16 + (dz - 1)) * 8 + dy] || dy < 7 && shouldPlaceSalt[(dx * 16 + dz) * 8 + (dy + 1)] || dy > 0 && shouldPlaceSalt[(dx * 16 + dz) * 8 + (dy - 1)]);
						if (!flag) continue;
						Material material = world.getBlockMaterial(x + dx, y + dy, z + dz);
						if (dy >= 4 && material.isLiquid()) {
							return false;
						}
						if (dy < 4 && !material.isSolid() && world.getBlockId(x + dx, y + dy, z + dz) != salt.id) {
							return false;
						}
						if (world.getBlockId(x + dx, y + dy, z + dz) != Block.ice.id) continue;
						return false;
					}
				}
			}
			for (dx = 0; dx < 16; ++dx) {
				for (int dz = 0; dz < 16; ++dz) {
					for (dy = 0; dy < 8; ++dy) {
						if (!shouldPlaceSalt[(dx * 16 + dz) * 8 + dy]) continue;
						world.setBlock(x + dx, y + dy, z + dz, dy < 4 ? salt.id : 0);
					}
				}
			}
		}
		return true;
	}
}
