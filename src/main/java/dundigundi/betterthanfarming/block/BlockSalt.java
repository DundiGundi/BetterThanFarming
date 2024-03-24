package dundigundi.betterthanfarming.block;

import dundigundi.betterthanfarming.item.BetterThanFarmingItems;
import net.minecraft.core.block.BlockSand;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class BlockSalt extends BlockSand {
	public BlockSalt (String key, int id){
		super(key, id);
	}

	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
		switch (dropCause) {
			case SILK_TOUCH:
			case PICK_BLOCK:
				return new ItemStack[]{new ItemStack(this)};
			default:
				return new ItemStack[]{new ItemStack(BetterThanFarmingItems.salt, 4)};
		}
	}
}
