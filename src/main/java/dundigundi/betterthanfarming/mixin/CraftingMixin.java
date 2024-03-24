package dundigundi.betterthanfarming.mixin;

import dundigundi.betterthanfarming.item.BetterThanFarmingItems;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryCraftingShapeless;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventoryCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = RecipeEntryCraftingShapeless.class, remap = false)
public abstract class CraftingMixin {

	/**
	 * @author DundiGundi
	 * @reason wanted to reduce durability of knife on crafting
	 */
	@Overwrite
	public ItemStack[] onCraftResult(InventoryCrafting inventorycrafting) {
		ItemStack[] returnStack = new ItemStack[9];
		for (int i = 0; i < inventorycrafting.getSizeInventory(); ++i) {
			ItemStack itemStack = inventorycrafting.getStackInSlot(i);
			if (itemStack == null) continue;
			if (itemStack.getItem().id == BetterThanFarmingItems.toolKnife.id){
				if (itemStack.getMetadata() < BetterThanFarmingItems.toolKnife.getMaxDamage() - 1){
					itemStack.damageItem(1, null);
					continue;
				}
			}
			inventorycrafting.decrStackSize(i, 1);
			if (!itemStack.getItem().hasContainerItem()) continue;
			inventorycrafting.setInventorySlotContents(i, new ItemStack(itemStack.getItem().getContainerItem()));
		}
		return returnStack;
	}

}
