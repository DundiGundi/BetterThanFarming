package dundigundi.betterthanfarming;

import dundigundi.betterthanfarming.block.BetterThanFarmingBlocks;
import dundigundi.betterthanfarming.block.entity.TileEntityStove;
import dundigundi.betterthanfarming.item.BetterThanFarmingItems;
import dundigundi.betterthanfarming.render.TileEntityRendererStove;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class BetterThanFarming implements ModInitializer, GameStartEntrypoint {
	public static String MOD_ID = "betterthanfarming";
	public static Logger logger = LoggerFactory.getLogger(MOD_ID);

	public BetterThanFarming(){
		EntityHelper.Core.createSpecialTileEntity(TileEntityStove.class, new TileEntityRendererStove(), "Stove");
	}
	@Override
	public void onInitialize() {
		logger.info("Better Than Farming has been initialised!");
	}

	@Override
	public void beforeGameStart() {
		new BetterThanFarmingBlocks().initializeBlocks();
		new BetterThanFarmingItems().initializeItems();
	}

	@Override
	public void afterGameStart() {

	}
}
