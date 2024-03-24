package dundigundi.betterthanfarming.item;

import net.minecraft.core.item.ItemFood;

public class ItemIngredient extends ItemFood {
	public int placeOnTexture;
	public ItemIngredient(String name, int id, int healAmount, boolean favouriteWolfMeat, int placeOnTexture) {
		super(name, id, healAmount, favouriteWolfMeat);
		this.placeOnTexture = placeOnTexture;
	}

	public int getPlaceOnTexture(){
		return this.placeOnTexture;
	}
}
