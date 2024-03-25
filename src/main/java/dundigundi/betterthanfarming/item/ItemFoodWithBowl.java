package dundigundi.betterthanfarming.item;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class ItemFoodWithBowl extends ItemFood {
	public ItemFoodWithBowl(String name, int id, int healAmount, boolean favouriteWolfMeat) {
		super(name, id, healAmount, favouriteWolfMeat);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (entityplayer.getHealth() < 20){
			entityplayer.inventory.insertItem(new ItemStack(Item.bowl, 1), false);
		}
		return super.onItemRightClick(itemstack, world, entityplayer);
	}
}
