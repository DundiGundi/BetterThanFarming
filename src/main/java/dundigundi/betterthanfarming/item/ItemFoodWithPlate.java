package dundigundi.betterthanfarming.item;

import dundigundi.betterthanfarming.catalyst.effects.BetterThanFarmingEffects;
import dundigundi.betterthanfarming.catalyst.effects.EffectRegeneration;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import sunsetsatellite.catalyst.effects.api.effect.Effects;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;

public class ItemFoodWithPlate extends ItemFood {
	public ItemFoodWithPlate(String name, int id, int healAmount, boolean favouriteWolfMeat) {
		super(name, id, healAmount, favouriteWolfMeat);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (entityplayer.getHealth() < entityplayer.getMaxHealth()){
			entityplayer.inventory.insertItem(new ItemStack(BetterThanFarmingItems.plate, 1), false);
		}
		IHasEffects effectEntity = (IHasEffects) entityplayer;
		EffectStack stack = new EffectStack(effectEntity, BetterThanFarmingEffects.regenerationEffect, 1);

		effectEntity.getContainer().add(stack);
		stack.start(effectEntity.getContainer());
		return super.onItemRightClick(itemstack, world, entityplayer);
	}
}
