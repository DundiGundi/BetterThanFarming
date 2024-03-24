package dundigundi.betterthanfarming.item;

import dundigundi.betterthanfarming.BetterThanFarmingTags;
import dundigundi.betterthanfarming.block.BetterThanFarmingBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class ItemToolKnife extends ItemTool {
	private int weaponDamage;
	public ItemToolKnife(String name, int id, int damageDealt, ToolMaterial toolMaterial) {
		super(name, id, damageDealt, toolMaterial, BetterThanFarmingTags.CUTTABLE_BY_KNIFE);
		this.maxStackSize = 1;
		this.setMaxDamage(toolMaterial.getDurability() - 1);
		this.weaponDamage = 2 + toolMaterial.getDamage();
		this.material = toolMaterial;
	}
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
		int id = world.getBlockId(blockX, blockY, blockZ);
		if (id == BetterThanFarmingBlocks.blockOfCheese.id){
			BetterThanFarmingBlocks.blockOfCheese.onBlockClicked(world, blockX, blockY, blockZ, entityplayer);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
		itemstack.damageItem(1, entityliving1);
		return true;
	}
	@Override
	public boolean onBlockDestroyed(World world, ItemStack itemstack, int id, int x, int y, int z, EntityLiving entity) {
		if (!(entity == null || entity.world.isClientSide || id != Block.tallgrass.id && id != Block.tallgrassFern.id)) {
			if (this.material.isSilkTouch()) {
				entity.world.dropItem(x, y, z, new ItemStack(Item.itemsList[id]));
				itemstack.damageItem(1, entity);
			} else if (entity.world.rand.nextInt(10) == 0) {
				entity.world.dropItem(x, y, z, new ItemStack(BetterThanFarmingItems.bacterium));
				itemstack.damageItem(1, entity);
			}
		}
		if (!(entity == null || entity.world.isClientSide || id != BetterThanFarmingBlocks.blockScallion.id)) {
			if (this.material.isSilkTouch()) {
				entity.world.dropItem(x, y, z, new ItemStack(Item.itemsList[id]));
				itemstack.damageItem(1, entity);
			} else {
				entity.world.dropItem(x, y, z, new ItemStack(BetterThanFarmingItems.foodScallion, 2));
				itemstack.damageItem(1, entity);
			}
		}
		return super.onBlockDestroyed(world, itemstack, id, x, y, z, entity);
	}

	@Override
	public int getDamageVsEntity(Entity entity) {
		return this.weaponDamage;
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public boolean canHarvestBlock(Block block) {
		return block.hasTag(BetterThanFarmingTags.CUTTABLE_BY_KNIFE) || block.id == Block.tallgrass.id || block.id == Block.tallgrassFern.id;
	}
}
