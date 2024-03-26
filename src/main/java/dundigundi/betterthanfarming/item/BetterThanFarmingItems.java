package dundigundi.betterthanfarming.item;


import dundigundi.betterthanfarming.BetterThanFarming;
import dundigundi.betterthanfarming.BetterThanFarmingConfig;
import dundigundi.betterthanfarming.block.BetterThanFarmingBlocks;
import dundigundi.betterthanfarming.misc.LookupCookingIngredients;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemFoodStackable;
import net.minecraft.core.item.ItemPlaceable;
import net.minecraft.core.item.ItemSeeds;
import net.minecraft.core.item.material.ToolMaterial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.ItemHelper;


public class BetterThanFarmingItems {
	public static final String MOD_ID = BetterThanFarming.MOD_ID;
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private int nextItemID(String itemName) {
		return BetterThanFarmingConfig.cfg.getInt("Item IDs." + itemName);
	}
	public static Item salt;
	public static Item rennet;
	public static Item cowStomach;
	public static Item bacterium;
	public static Item cheeseBlock;
	//tools and utensils
	public static Item toolKnife;
	public static Item plate;
	//seeds
	public static Item seedsWatermelon;
	//crops and meats
	public static Item foodCheeseSlice;
	public static Item foodScallion;
	public static Item foodBeefRaw;
	public static Item foodBeefCooked;
	public static Item foodChickenRaw;
	public static Item foodChickenCooked;
	public static Item foodMuttonRaw;
	public static Item foodMuttonCooked;
	public static Item foodCalamariRaw;
	public static Item foodCalamariCooked;
	public static Item foodWatermelonSlice;
	//dishes
	public static Item foodBeefBourguignon;
	public static Item foodIrishStew;
	public static Item foodTikkaMassala;
	public static Item foodFullEnglish;

	public void initializeItems() {
		salt = ItemHelper.createItem(MOD_ID, new Item("salt",nextItemID( "salt")), "salt.png").setMaxStackSize(64);
		cowStomach = ItemHelper.createItem(MOD_ID, new Item("cowStomach", nextItemID("cowStomach")), "cowStomach.png").setMaxStackSize(1);
		rennet = ItemHelper.createItem(MOD_ID, new Item("rennet", nextItemID("rennet")),"rennet.png").setMaxStackSize(16);
		bacterium = ItemHelper.createItem(MOD_ID, new Item("bacterium", nextItemID("bacterium")), "bacterium.png").setMaxStackSize(16);

		cheeseBlock = ItemHelper.createItem(MOD_ID, new ItemPlaceable("cheeseBlock", nextItemID("cheeseBlock"), BetterThanFarmingBlocks.blockOfCheese).setMaxStackSize(1), "cheeseBlock.png");

		//tools and utensils
		toolKnife = ItemHelper.createItem(MOD_ID, new ItemToolKnife("tool.knife", nextItemID("toolKnife"), 2, ToolMaterial.wood), "knife.png");
		plate = ItemHelper.createItem(MOD_ID, new Item("plate", nextItemID("plate")), "plate.png");

		//seeds
		seedsWatermelon = ItemHelper.createItem(MOD_ID, new ItemSeeds("seeds.watermelon", nextItemID("seedsWatermelon"), BetterThanFarmingBlocks.cropsWatermelon), "seeds_watermelon.png");

		//crops and meats
		foodScallion = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("food.scallion", nextItemID("foodScallion"), 1, false, 8	), "scallion.png");
		foodCheeseSlice = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("food.cheese.slice",nextItemID("foodCheeseSlice"), 1, false, 8), "cheeseSlice.png");
		foodBeefRaw = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("food.beef.raw", nextItemID("foodBeefRaw"), 2, true, 4), "beef_raw.png");
		foodBeefCooked = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("food.beef.cooked", nextItemID("foodBeefCooked"), 8, true, 4), "beef_cooked.png");
		foodChickenRaw = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("food.chicken.raw", nextItemID("foodChickenRaw"), 2, true, 4), "chicken_raw.png");
		foodChickenCooked = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("food.chicken.cooked", nextItemID("foodChickenCooked"), 6, true, 4), "chicken_cooked.png");
		foodMuttonRaw = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("food.mutton.raw", nextItemID("foodMuttonRaw"), 2, true, 4), "mutton_raw.png");
		foodMuttonCooked = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("food.mutton.cooked", nextItemID("foodMuttonCooked"), 5, true, 4), "mutton_cooked.png");
		Item.foodPorkchopRaw = new ItemFoodStackable("food.porkchop.raw", 16447, 2, true, 4).setIconCoord(7, 5);
		Item.foodPorkchopCooked = new ItemFoodStackable("food.porkchop.cooked", 16448, 6, true, 4).setIconCoord(8, 5);
		foodCalamariRaw = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("food.calamari.raw", nextItemID("foodCalamariRaw"), 1, true, 8), "calamari_raw.png");
		foodCalamariCooked = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("food.calamari.cooked", nextItemID("foodCalamariCooked"), 3, true, 8), "calamari_cooked.png");
		foodWatermelonSlice = ItemHelper.createItem(MOD_ID, new ItemFoodWatermelonSlice("food.watermelon.slice", nextItemID("foodWatermelonSlice"), 2, false, 8), "watermelonSlice.png");

		//dishes
		foodBeefBourguignon = ItemHelper.createItem(MOD_ID, new ItemFoodWithBowl("food.beefBourguignon", nextItemID("foodBeefBourguignon"), 10, false), "beefBourguignon.png");
		foodIrishStew = ItemHelper.createItem(MOD_ID, new ItemFoodWithBowl("food.irishStew", nextItemID("foodIrishStew"), 8, false), "irishStew.png");
		foodTikkaMassala = ItemHelper.createItem(MOD_ID, new ItemFoodWithBowl("food.tikkaMassala", nextItemID("foodTikkaMassala"), 7, false), "tikkaMassala.png");
		foodFullEnglish = ItemHelper.createItem(MOD_ID, new ItemFoodWithPlate("food.fullEnglish", nextItemID("foodFullEnglish"), 15, false), "fullEnglish.png");


		//adding ingredients to the ingredientList to be able to cook them on stove
		LookupCookingIngredients.instance.addIngredientEntry(16447, Item.foodPorkchopCooked); //adding porkchop
		LookupCookingIngredients.instance.addIngredientEntry(BetterThanFarmingItems.foodBeefRaw.id, BetterThanFarmingItems.foodBeefCooked);
		LookupCookingIngredients.instance.addIngredientEntry(BetterThanFarmingItems.foodChickenRaw.id, BetterThanFarmingItems.foodChickenCooked);
		LookupCookingIngredients.instance.addIngredientEntry(BetterThanFarmingItems.foodMuttonRaw.id, BetterThanFarmingItems.foodMuttonCooked);
		LookupCookingIngredients.instance.addIngredientEntry(BetterThanFarmingItems.foodCalamariRaw.id, BetterThanFarmingItems.foodCalamariCooked);
	}
}
