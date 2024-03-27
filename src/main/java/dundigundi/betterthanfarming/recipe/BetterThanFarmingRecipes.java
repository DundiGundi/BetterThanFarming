package dundigundi.betterthanfarming.recipe;

import dundigundi.betterthanfarming.BetterThanFarming;
import dundigundi.betterthanfarming.block.BetterThanFarmingBlocks;
import dundigundi.betterthanfarming.item.BetterThanFarmingItems;
import net.minecraft.core.block.Block;
import net.minecraft.core.data.DataLoader;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCrafting;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.RecipeEntrypoint;

import static dundigundi.betterthanfarming.item.BetterThanFarmingItems.plate;

public class BetterThanFarmingRecipes implements RecipeEntrypoint {
	public static final String MOD_ID = BetterThanFarming.MOD_ID;
	public static final RecipeNamespace BETTERTHANFARMING = new RecipeNamespace();
	public static final RecipeGroup<RecipeEntryCrafting<?, ?>> WORKBENCH = new RecipeGroup<>(new RecipeSymbol(new ItemStack(Block.workbench)));
	@Override
	public void onRecipesReady() {
		//WorkbenchRecipes:
		Registries.ITEM_GROUPS.register("betterthanfarming:toolKnife", Registries.stackListOf());
		for (int i = 0; i < BetterThanFarmingItems.toolKnife.getMaxDamage() + 1; i++) {
			ItemStack j = BetterThanFarmingItems.toolKnife.getDefaultStack();
			j.setMetadata(i);
			Registries.ITEM_GROUPS.getItem("betterthanfarming:toolKnife").add(j);
		}

		BETTERTHANFARMING.register("workbench", WORKBENCH );
		Registries.RECIPES.register("betterthanfarming", BETTERTHANFARMING);
		DataLoader.loadRecipesFromFile("/assets/betterthanfarming/recipes/workbench/recipes.json");

		RecipeBuilder.Shaped(MOD_ID)
				.setShape("CHC", "CBC", "SSS")
				.addInput('C', Block.cobbleStone)
				.addInput('H', Item.toolShovelWood)
				.addInput('B', Item.bucket)
				.addInput('S', Block.slabCobbleStone)
				.setConsumeContainer(false)
				.create("cheeseMaker", BetterThanFarmingBlocks.cheeseMaker.getDefaultStack());
		RecipeBuilder.Shaped(MOD_ID)
				.setShape(" F", "S ")
				.addInput('F', Item.flint)
				.addInput('S', Item.stick)
				.create("knife", BetterThanFarmingItems.toolKnife.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
				.setShape("AM", "BB", "W")
				.addInput('A', Item.bucketWater)
				.addInput('M', Block.mushroomBrown)
				.addInput('B', BetterThanFarmingItems.foodBeefCooked)
				.addInput('W', Item.bowl)
				.setConsumeContainer(false)
				.create("beefBourguignon", BetterThanFarmingItems.foodBeefBourguignon.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
				.setShape("AU", "W")
				.addInput('A', Item.bucketWater)
				.addInput('U', BetterThanFarmingItems.foodMuttonCooked)
				.addInput('W', Item.bowl)
				.setConsumeContainer(false)
				.create("irishStew", BetterThanFarmingItems.foodIrishStew.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
				.setShape("PG", "W")
				.addInput('P', BetterThanFarmingItems.foodChickenCooked)
				.addInput('G', Item.seedsPumpkin)
				.addInput('W', Item.bowl)
				.setConsumeContainer(false)
				.create("tikkaMassala", BetterThanFarmingItems.foodTikkaMassala.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
				.setShape("Q Q"," Q " )
				.addInput('Q', Item.quartz)
				.create("plate", plate.getDefaultStack());

		RecipeBuilder.Shaped(MOD_ID)
				.setShape("PCB", " L ")
				.addInput('P', Item.foodPorkchopCooked)
				.addInput('C', Item.cherry)
				.addInput('B', Item.foodBread)
				.addInput('L', plate)
				.setConsumeContainer(false)
				.create("fullEnglishBreakfast", BetterThanFarmingItems.foodFullEnglish.getDefaultStack());

		RecipeBuilder.Shapeless(MOD_ID)
				.addInput(BetterThanFarmingItems.foodWatermelonSlice)
				.create("seedsWatermelon", BetterThanFarmingItems.seedsWatermelon.getDefaultStack());

		//FurnaceRecipes:
		RecipeBuilder.Furnace(MOD_ID)
				.setInput(BetterThanFarmingItems.foodBeefRaw)
				.create("cookBeef", (BetterThanFarmingItems.foodBeefCooked).getDefaultStack());

		RecipeBuilder.Furnace(MOD_ID)
				.setInput(BetterThanFarmingItems.foodChickenRaw)
				.create("cookChicken", (BetterThanFarmingItems.foodChickenCooked).getDefaultStack());

		RecipeBuilder.Furnace(MOD_ID)
				.setInput(BetterThanFarmingItems.foodMuttonRaw)
				.create("cookMutton", (BetterThanFarmingItems.foodMuttonCooked).getDefaultStack());
		RecipeBuilder.Furnace(MOD_ID)
				.setInput(BetterThanFarmingItems.foodCalamariRaw)
				.create("cookMutton", (BetterThanFarmingItems.foodCalamariCooked).getDefaultStack());

		//BlastFurnaceRecipes:
		RecipeBuilder.BlastFurnace(MOD_ID)
				.setInput(BetterThanFarmingItems.foodBeefRaw)
				.create("cookBeef", (BetterThanFarmingItems.foodBeefCooked).getDefaultStack());

		RecipeBuilder.BlastFurnace(MOD_ID)
				.setInput(BetterThanFarmingItems.foodChickenRaw)
				.create("cookChicken", (BetterThanFarmingItems.foodChickenCooked).getDefaultStack());

		RecipeBuilder.BlastFurnace(MOD_ID)
				.setInput(BetterThanFarmingItems.foodMuttonRaw)
				.create("cookMutton", (BetterThanFarmingItems.foodMuttonCooked).getDefaultStack());
		RecipeBuilder.BlastFurnace(MOD_ID)
				.setInput(BetterThanFarmingItems.foodCalamariRaw)
				.create("cookMutton", (BetterThanFarmingItems.foodCalamariCooked).getDefaultStack());
	}

}
