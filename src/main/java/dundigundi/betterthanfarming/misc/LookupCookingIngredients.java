package dundigundi.betterthanfarming.misc;

import net.minecraft.core.item.Item;

import java.util.HashMap;
import java.util.Map;

public class LookupCookingIngredients {
	public static final LookupCookingIngredients instance = new LookupCookingIngredients();
	protected final Map<Integer, Item> ingredientList = new HashMap<Integer, Item>();
	protected LookupCookingIngredients() {
		this.register();
	}
	protected void register() {

	}

	public void addIngredientEntry(int id, Item resultItem) {
		this.ingredientList.put(id, resultItem);
	}

	public Item getResults(int id) {
		return this.ingredientList.get(id) == null ? null : this.ingredientList.get(id);
	}

	public Map<Integer, Item> getIngredientList() {
		return this.ingredientList;
	}
}
