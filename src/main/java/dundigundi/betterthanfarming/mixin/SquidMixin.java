package dundigundi.betterthanfarming.mixin;

import dundigundi.betterthanfarming.item.BetterThanFarmingItems;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.animal.EntitySquid;
import net.minecraft.core.entity.animal.EntityWaterAnimal;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntitySquid.class, remap = false)
public abstract class SquidMixin extends EntityWaterAnimal {
	@Shadow
	protected abstract void dropFewItems();

	public SquidMixin(World world) {
		super(world);
	}

	public int getDropItemId() {
		if (this.remainingFireTicks > 0) {
			return BetterThanFarmingItems.foodCalamariCooked.id;
		}
		return BetterThanFarmingItems.foodCalamariRaw.id;
	}

	@Inject(method = "dropFewItems", at = @At("HEAD"))
	private void dropSquidMeat(CallbackInfo ci){
		int i;
		if ((i = this.getDropItemId()) > 0) {
			int j = 2 + this.random.nextInt(2);
			for (int k = 0; k < j; ++k) {
				this.spawnAtLocation(i, 1);
			}
		}
	}
}
