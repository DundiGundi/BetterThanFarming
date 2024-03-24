package dundigundi.betterthanfarming.mixin;

import dundigundi.betterthanfarming.item.BetterThanFarmingItems;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.animal.EntitySheep;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(value = EntitySheep.class, remap = false)
public abstract class SheepMixin  extends EntityAnimal {
	private Random random = new Random();

	public SheepMixin(World world) {
		super(world);
	}
	public int getDropItemId() {
		if (this.remainingFireTicks > 0) {
			return BetterThanFarmingItems.foodMuttonCooked.id;
		}
		return BetterThanFarmingItems.foodMuttonRaw.id;
	}
	@Inject(method = "dropFewItems", at = @At("HEAD"))
	public void dropMeat(CallbackInfo ci){
		int i;
		if ((i = this.getDropItemId()) > 0) {
			int j = 1 + this.random.nextInt(2);
			for (int k = 0; k < j; ++k) {
				this.spawnAtLocation(i, 1);
			}
		}
	}
}
