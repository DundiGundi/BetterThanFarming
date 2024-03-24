package dundigundi.betterthanfarming.mixin;

import dundigundi.betterthanfarming.interfaces.IChicken;
import dundigundi.betterthanfarming.item.BetterThanFarmingItems;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.animal.EntityChicken;
import net.minecraft.core.item.Item;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EntityChicken.class, remap = false)
public abstract class ChickenMixin  extends EntityAnimal implements IChicken {

	public ChickenMixin(World world) {
		super(world);
	}

	public int getDropItemId() {
		if (this.remainingFireTicks > 0) {
			return BetterThanFarmingItems.foodChickenCooked.id;
		}
		return BetterThanFarmingItems.foodChickenRaw.id;
	}

	@Override
	public void dropFewItems() {
		int i;
		if ((i = this.getDropItemId()) > 0) {
			this.spawnAtLocation(i, 1);
		}
		this.spawnAtLocation(Item.featherChicken.id, 1);
	}
}
