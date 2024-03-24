package dundigundi.betterthanfarming.compat.terrainapi;

import dundigundi.betterthanfarming.BetterThanFarming;
import useless.terrainapi.api.TerrainAPI;

public class TerrainAPIPlugin implements TerrainAPI {
	@Override
	public String getModID() {
		return BetterThanFarming.MOD_ID;
	}

	@Override
	public void onInitialize() {
		new OverworldInitialization().init();
	}
}
