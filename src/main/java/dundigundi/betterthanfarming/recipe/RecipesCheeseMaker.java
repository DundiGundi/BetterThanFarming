package dundigundi.betterthanfarming.recipe;

import dundigundi.betterthanfarming.item.BetterThanFarmingItems;
import net.minecraft.core.item.ItemStack;

import java.util.HashMap;

public class RecipesCheeseMaker {
	private static final HashMap<Integer, ItemStack> recipeList = new HashMap<>();

	public RecipesCheeseMaker() {
		addRecipe(BetterThanFarmingItems.salt.id, new ItemStack(BetterThanFarmingItems.cheeseBlock, 1));
	}

	public static void addRecipe(int input, ItemStack output) {
		recipeList.put(input, output);
	}

	public ItemStack getRecipeResult(int i) {
		return recipeList.get(i);
	}

	public HashMap<Integer, ItemStack> getRecipeList() {
		return recipeList;
	}
}
