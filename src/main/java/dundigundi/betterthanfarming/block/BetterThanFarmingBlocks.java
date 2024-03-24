package dundigundi.betterthanfarming.block;

import dundigundi.betterthanfarming.BetterThanFarming;
import dundigundi.betterthanfarming.BetterThanFarmingConfig;
import dundigundi.betterthanfarming.BetterThanFarmingTags;
import dundigundi.betterthanfarming.block.entity.TileEntityCheeseMaker;
import dundigundi.betterthanfarming.block.entity.TileEntityStove;
import dundigundi.betterthanfarming.gui.ContainerCheeseMaker;
import dundigundi.betterthanfarming.gui.ContainerStove;
import dundigundi.betterthanfarming.gui.GuiCheeseMaker;
import dundigundi.betterthanfarming.gui.GuiStove;
import net.minecraft.client.render.block.model.BlockModelRenderBlocks;
import net.minecraft.client.sound.block.BlockSounds;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockTallGrass;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sunsetsatellite.catalyst.Catalyst;
import sunsetsatellite.catalyst.core.util.MpGuiEntry;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.EntityHelper;


public class BetterThanFarmingBlocks {
	public static final String MOD_ID = BetterThanFarming.MOD_ID;
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private int nextBlockID(String blockName) {
		return BetterThanFarmingConfig.cfg.getInt("Block IDs." + blockName);
	}

	//salt
	public static Block oreSaltStone;
	public static Block oreSaltBasalt;
	public static Block oreSaltLimestone;
	public static Block oreSaltGranite;
	public static Block blockSalt;

	//Food
	public static Block blockOfCheese;
	public static Block blockScallion;

	//Machines
	public static Block cheeseMaker;
	public static Block stoveIdle;
	public static Block stoveActive;

	private void pickaxeLevels() {
		ItemToolPickaxe.miningLevels.put(oreSaltStone, 1);
		ItemToolPickaxe.miningLevels.put(oreSaltBasalt, 1);
		ItemToolPickaxe.miningLevels.put(oreSaltLimestone, 1);
		ItemToolPickaxe.miningLevels.put(oreSaltGranite, 1);
	}
	private void registerGUIs() {
		Catalyst.GUIS.register("CheeseMaker", new MpGuiEntry(TileEntityCheeseMaker.class, GuiCheeseMaker.class, ContainerCheeseMaker.class));
		Catalyst.GUIS.register("Stove", new MpGuiEntry(TileEntityStove.class, GuiStove.class, ContainerStove.class));
	}
	private void initializeTiles() {
		EntityHelper.Core.createTileEntity(TileEntityCheeseMaker.class, "CheeseMaker");
		EntityHelper.Core.createTileEntity(TileEntityStove.class, "stove");
	}

	public void initializeBlocks() {

		BlockBuilder oreBuilder = new BlockBuilder(MOD_ID)
				.setBlockSound(BlockSounds.STONE)
				.setResistance(3f)
				.setHardness(3f)
				.setTags(BlockTags.MINEABLE_BY_PICKAXE);

		BlockBuilder cheeseBuilder = new BlockBuilder(MOD_ID)
				.setTopBottomTexture("blockOfCheeseTopBottom.png")
				.setSideTextures("blockOfCheeseSide.png")
				.setResistance(0.5f)
				.setHardness(0.5f);

		BlockBuilder sandBuilder = new BlockBuilder(MOD_ID)
				.setBlockSound(BlockSounds.SAND)
				.setResistance(0.5f)
				.setHardness(0.5f)
				.setTags(BlockTags.MINEABLE_BY_SHOVEL, BlockTags.CAVES_CUT_THROUGH);

		BlockBuilder machineBuilder = new BlockBuilder(MOD_ID)
				.setBlockSound(BlockSounds.WOOD)
				.setTextures(11, 3)
				.setResistance(2.5f)
				.setHardness(2.5f)
				.setTags(BlockTags.MINEABLE_BY_AXE);

		BlockBuilder plantBuilder = new BlockBuilder(MOD_ID)
				.setBlockSound(BlockSounds.GRASS)
				.setHardness(0.0f)
				.setTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.PLACE_OVERWRITES, BlockTags.SHEARS_DO_SILK_TOUCH);

		oreSaltStone = oreBuilder
				.setTextures("oreSalt_stone.png")
				.build(new BlockOreSalt("ore.salt.stone", nextBlockID("oreSaltStone"), Material.stone));
		oreSaltBasalt = oreBuilder
				.setTextures("oreSalt_basalt.png")
				.build(new BlockOreSalt("ore.salt.basalt", nextBlockID("oreSaltBasalt"), Material.stone));
		oreSaltLimestone = oreBuilder
				.setTextures("oreSalt_limestone.png")
				.build(new BlockOreSalt("ore.salt.limestone", nextBlockID("oreSaltLimestone"), Material.stone));
		oreSaltGranite = oreBuilder
				.setTextures("oreSalt_granite.png")
				.build(new BlockOreSalt("ore.salt.granite", nextBlockID("oreSaltGranite"), Material.stone));
		blockSalt = sandBuilder
				.setTextures("block_salt.png")
				.build(new BlockSalt("block.salt", nextBlockID("blockSalt")));

		blockOfCheese = cheeseBuilder
				.build(new BlockBlockofCheese("blockOfCheese", nextBlockID("blockOfCheese"))
						.withDisabledStats()
						.withDisabledNeighborNotifyOnMetadataChange()
						.withTags(BlockTags.BROKEN_BY_FLUIDS, BlockTags.NOT_IN_CREATIVE_MENU, BetterThanFarmingTags.CUTTABLE_BY_KNIFE));
		cheeseMaker = machineBuilder
				.setTextures("cheeseMakerSides.png")
				.setTopTexture("cheeseMakerTop.png")
				.build(new BlockCheeseMaker("cheeseMaker", nextBlockID("cheeseMaker"), Material.wood)
						.withTags(BlockTags.MINEABLE_BY_PICKAXE));
		stoveIdle = machineBuilder
				//.setTextures("test.png")
				.build(new BlockStove("stove.idle", nextBlockID("stoveIdle"),false)
						.withHardness(3.5f)
						.withDisabledNeighborNotifyOnMetadataChange()
						.withImmovableFlagSet()
						.withTags(BlockTags.MINEABLE_BY_PICKAXE));
		stoveActive = machineBuilder
				.setTextures("test.png")
				.build(new BlockStove("stove.active", nextBlockID("stoveActive"), true)
						.withHardness(3.5f)
						.withLightEmission(0.875f)
						.withDisabledNeighborNotifyOnMetadataChange()
						.withImmovableFlagSet()
						.withTags(BlockTags.NOT_IN_CREATIVE_MENU, BlockTags.MINEABLE_BY_PICKAXE));
		blockScallion = plantBuilder
				.setTextures("scallionPlant.png")
				.setBlockModel(new BlockModelRenderBlocks(1))
				.build(new BlockTallGrass("block.scallion", nextBlockID("blockScallion"))
						.setKilledByWeather()
						.withTags(BetterThanFarmingTags.CUTTABLE_BY_KNIFE));

		registerGUIs();
		initializeTiles();
		pickaxeLevels();
	}
}