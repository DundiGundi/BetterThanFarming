package dundigundi.betterthanfarming.compat.terrainapi;

import dundigundi.betterthanfarming.BetterThanFarming;
import dundigundi.betterthanfarming.block.BetterThanFarmingBlocks;
import dundigundi.betterthanfarming.world.WorldFeatureMelon;
import dundigundi.betterthanfarming.world.WorldFeatureSaltLake;
import dundigundi.betterthanfarming.world.WorldFeatureScallionPlant;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import useless.terrainapi.generation.overworld.api.ChunkDecoratorOverworldAPI;
import useless.terrainapi.initialization.BaseInitialization;

public class OverworldInitialization extends BaseInitialization {
	private final String MOD_ID = BetterThanFarming.MOD_ID;

	@Override
	protected void initValues() {

	}

	@Override
	protected void initStructure() {

	}

	@Override
	protected void initOre() {
		ChunkDecoratorOverworldAPI.oreFeatures.addManagedOreFeature(MOD_ID, BetterThanFarmingBlocks.oreSaltStone, 4, 10, 1, true);
	}

	@Override
	protected void initRandom() {
		ChunkDecoratorOverworldAPI.randomFeatures.addFeature(new WorldFeatureScallionPlant(), 1, -1, 1,
				new Biome[]{Biomes.OVERWORLD_BIRCH_FOREST, Biomes.OVERWORLD_RETRO, Biomes.OVERWORLD_MEADOW, Biomes.OVERWORLD_FOREST, Biomes.OVERWORLD_SEASONAL_FOREST});
		ChunkDecoratorOverworldAPI.randomFeatures.addFeature(new WorldFeatureSaltLake(), 1, -1, 1,
				new Biome[]{Biomes.OVERWORLD_DESERT});
		ChunkDecoratorOverworldAPI.randomFeatures.addFeature(new WorldFeatureMelon(), 1, -1, 1,
				new Biome[]{Biomes.OVERWORLD_RAINFOREST});
	}

	@Override
	protected void initBiome() {

	}
}
