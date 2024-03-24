package dundigundi.betterthanfarming.item;


import dundigundi.betterthanfarming.BetterThanFarming;
import dundigundi.betterthanfarming.BetterThanFarmingConfig;
import dundigundi.betterthanfarming.block.BetterThanFarmingBlocks;
import dundigundi.betterthanfarming.misc.LookupCookingIngredients;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.item.ItemFoodStackable;
import net.minecraft.core.item.ItemPlaceable;
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
	public static Item toolKnife;
	public static Item foodCheeseSlice;
	public static Item foodScallion;
	public static Item foodBeefRaw;
	public static Item foodBeefCooked;
	public static Item foodChickenRaw;
	public static Item foodChickenCooked;
	public static Item foodMuttonRaw;
	public static Item foodMuttonCooked;

	public void initializeItems() {
		salt = ItemHelper.createItem(MOD_ID, new Item("salt",nextItemID( "salt")), "salt.png").setMaxStackSize(64);
		cowStomach = ItemHelper.createItem(MOD_ID, new Item("cowStomach", nextItemID("cowStomach")), "cowStomach.png").setMaxStackSize(1);
		rennet = ItemHelper.createItem(MOD_ID, new Item("rennet", nextItemID("rennet")),"rennet.png").setMaxStackSize(16);
		bacterium = ItemHelper.createItem(MOD_ID, new Item("bacterium", nextItemID("bacterium")), "bacterium.png").setMaxStackSize(16);

		cheeseBlock = ItemHelper.createItem(MOD_ID, new ItemPlaceable("cheeseBlock", nextItemID("cheeseBlock"), BetterThanFarmingBlocks.blockOfCheese).setMaxStackSize(1), "cheeseBlock.png");

		toolKnife = ItemHelper.createItem(MOD_ID, new ItemToolKnife("tool.knife", nextItemID("toolKnife"), 2, ToolMaterial.wood), "knife.png");

		foodScallion = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("food.scallion", nextItemID("foodScallion"), 1, false, 4), "scallion.png");
		foodCheeseSlice = ItemHelper.createItem(MOD_ID, new ItemFoodStackable("food.cheeseSlice",nextItemID("foodCheeseSlice"), 1, false, 4), "cheeseSlice.png");
		foodBeefRaw = ItemHelper.createItem(MOD_ID, new ItemIngredient("food.beef.raw", nextItemID("foodBeefRaw"), 3, true, 1), "beef_raw.png");
		foodBeefCooked = ItemHelper.createItem(MOD_ID, new ItemFood("food.beef.cooked", nextItemID("foodBeefCooked"), 8, true), "beef_cooked.png");
		foodChickenRaw = ItemHelper.createItem(MOD_ID, new ItemIngredient("food.chicken.raw", nextItemID("foodChickenRaw"), 3, true, 2), "chicken_raw.png");
		foodChickenCooked = ItemHelper.createItem(MOD_ID, new ItemFood("food.chicken.cooked", nextItemID("foodChickenCooked"), 10, true), "chicken_cooked.png");
		foodMuttonRaw = ItemHelper.createItem(MOD_ID, new ItemIngredient("food.mutton.raw", nextItemID("foodMuttonRaw"), 3, true, 3), "mutton_raw.png");
		foodMuttonCooked = ItemHelper.createItem(MOD_ID, new ItemFood("food.mutton.cooked", nextItemID("foodMuttonCooked"), 8, true), "mutton_cooked.png");

		//adding ingredients to the ingredientList to be able to cook them on stove
		LookupCookingIngredients.instance.addIngredientEntry(16447, Item.foodPorkchopCooked); //adding porkchop
		LookupCookingIngredients.instance.addIngredientEntry(BetterThanFarmingItems.foodBeefRaw.id, BetterThanFarmingItems.foodBeefCooked);
		LookupCookingIngredients.instance.addIngredientEntry(BetterThanFarmingItems.foodChickenRaw.id, BetterThanFarmingItems.foodChickenCooked);
		LookupCookingIngredients.instance.addIngredientEntry(BetterThanFarmingItems.foodMuttonRaw.id, BetterThanFarmingItems.foodMuttonCooked);

	}
}
