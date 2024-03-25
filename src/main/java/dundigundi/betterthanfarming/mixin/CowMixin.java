package dundigundi.betterthanfarming.mixin;

import dundigundi.betterthanfarming.item.BetterThanFarmingItems;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.animal.EntityCow;
import net.minecraft.core.item.Item;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(value = EntityCow.class, remap = false)
public abstract class CowMixin  extends EntityAnimal {
	private Random random = new Random();

	public CowMixin(World world) {
		super(world);
	}
	public int getDropItemId() {
		if (this.remainingFireTicks > 0) {
			return BetterThanFarmingItems.foodBeefCooked.id;
		}
		return BetterThanFarmingItems.foodBeefRaw.id;
	}
	@Inject(method = "dropFewItems", at = @At("HEAD"))
	public void dropStomachAndSteak(CallbackInfo ci){
		int i;
		if (random.nextInt(3) == 0){
			spawnAtLocation(BetterThanFarmingItems.cowStomach.id, 1);
		}
		if ((i = this.getDropItemId()) > 0) {
			int j = 1 + this.random.nextInt(3);
			for (int k = 0; k < j; ++k) {
				this.spawnAtLocation(i, 1);
			}
		}
		if ((i = Item.leather.id) > 0) {
			int j = this.random.nextInt(4) + 1;
			for (int k = 0; k < j; ++k) {
				this.spawnAtLocation(i, 1);
			}
		}
	}
}
