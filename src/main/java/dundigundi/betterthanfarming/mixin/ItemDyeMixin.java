package dundigundi.betterthanfarming.mixin;

import dundigundi.betterthanfarming.block.BetterThanFarmingBlocks;
import dundigundi.betterthanfarming.block.crops.BlockCropsPotato;
import dundigundi.betterthanfarming.block.crops.BlockCropsTurnip;
import dundigundi.betterthanfarming.block.crops.BlockCropsWatermelon;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockCropsPumpkin;
import net.minecraft.core.block.BlockCropsWheat;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemDye;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemDye.class, remap = false)
public class ItemDyeMixin {
	@Inject(method = "onItemUse", at = @At("HEAD"), cancellable = true)
	private void addCropFertilization(ItemStack itemstack, EntityPlayer entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced, CallbackInfoReturnable<Boolean> cir){
		if (itemstack.getMetadata() == 15) {
			int id = world.getBlockId(blockX, blockY, blockZ);
			int meta = world.getBlockMetadata(blockX, blockY, blockZ);
			if (id == BetterThanFarmingBlocks.cropsWatermelon.id && meta < 5) {
				if (!world.isClientSide) {
					((BlockCropsWatermelon)BetterThanFarmingBlocks.cropsWatermelon).fertilize(world, blockX, blockY, blockZ);
					if (entityplayer.getGamemode().consumeBlocks()) {
						--itemstack.stackSize;
					}
				}
				cir.setReturnValue(true);
			}
			if (id == BetterThanFarmingBlocks.cropsPotato.id && meta < 3) {
				if (!world.isClientSide) {
					((BlockCropsPotato)BetterThanFarmingBlocks.cropsPotato).fertilize(world, blockX, blockY, blockZ);
					if (entityplayer.getGamemode().consumeBlocks()) {
						--itemstack.stackSize;
					}
				}
				cir.setReturnValue(true);
			}
			if (id == BetterThanFarmingBlocks.cropsTurnip.id && meta < 3) {
				if (!world.isClientSide) {
					((BlockCropsTurnip)BetterThanFarmingBlocks.cropsTurnip).fertilize(world, blockX, blockY, blockZ);
					if (entityplayer.getGamemode().consumeBlocks()) {
						--itemstack.stackSize;
					}
				}
				cir.setReturnValue(true);
			}
		}
	}
}
