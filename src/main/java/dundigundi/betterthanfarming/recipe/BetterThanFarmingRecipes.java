package dundigundi.betterthanfarming.recipe;

import dundigundi.betterthanfarming.item.BetterThanFarmingItems;
import net.minecraft.core.block.Block;
import net.minecraft.core.data.DataLoader;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeGroup;
import net.minecraft.core.data.registry.recipe.RecipeNamespace;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCrafting;
import net.minecraft.core.item.ItemStack;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class BetterThanFarmingRecipes implements RecipeEntrypoint {
	public static final RecipeNamespace BETTERTHANFARMING = new RecipeNamespace();
	public static final RecipeGroup<RecipeEntryCrafting<?, ?>> WORKBENCH = new RecipeGroup<>(new RecipeSymbol(new ItemStack(Block.workbench)));
	@Override
	public void onRecipesReady() {
		Registries.ITEM_GROUPS.register("betterthanfarming:toolKnife", Registries.stackListOf());
		for (int i = 0; i < BetterThanFarmingItems.toolKnife.getMaxDamage() + 1; i++) {
			ItemStack j = BetterThanFarmingItems.toolKnife.getDefaultStack();
			j.setMetadata(i);
			Registries.ITEM_GROUPS.getItem("betterthanfarming:toolKnife").add(j);
		}
		BETTERTHANFARMING.register("workbench", WORKBENCH );
		Registries.RECIPES.register("betterthanfarming", BETTERTHANFARMING);
		DataLoader.loadRecipes("/assets/betterthanfarming/recipes/workbench/recipes.json");
		//LookupFuelFurnace.instance.addFuelEntry(CheeseModItems.brownCoal.id, 800); igy kell fuelt hozzáadni :)
	}

}
