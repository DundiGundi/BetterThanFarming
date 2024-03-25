package dundigundi.betterthanfarming.catalyst.effects;

import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.helper.DamageType;
import sunsetsatellite.catalyst.effects.api.effect.Effect;
import sunsetsatellite.catalyst.effects.api.effect.EffectContainer;
import sunsetsatellite.catalyst.effects.api.effect.EffectStack;
import sunsetsatellite.catalyst.effects.api.effect.EffectTimeType;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;

import java.util.List;
import java.util.Random;

public class EffectRegeneration extends Effect {
	private int elapsedTicks = 0;
	private int effectApplyingTick = 40;

	public EffectRegeneration(String nameKey, String id, String imagePath, int color, List<Modifier<?>> modifiers, EffectTimeType effectTimeType, int defaultDuration, int maxStack) {
		super(nameKey, id, imagePath, color, modifiers, effectTimeType, defaultDuration, maxStack);
	}

	@Override
	public <T> void tick(EffectStack effectStack, EffectContainer<T> effectContainer) {
		if (effectStack.getEffect() != this) return;
		if (!(effectContainer.getParent() instanceof EntityLiving)) return;
		EntityLiving entityLiving = (EntityLiving) effectContainer.getParent();
		if (elapsedTicks == effectApplyingTick && entityLiving.health < 20){
			entityLiving.heal(1);
			elapsedTicks = 0;
		}
		elapsedTicks++;
	}
}
