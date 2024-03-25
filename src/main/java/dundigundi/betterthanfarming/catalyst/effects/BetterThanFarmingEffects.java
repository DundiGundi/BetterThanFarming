package dundigundi.betterthanfarming.catalyst.effects;

import sunsetsatellite.catalyst.CatalystEffects;
import sunsetsatellite.catalyst.effects.api.effect.EffectTimeType;
import sunsetsatellite.catalyst.effects.api.effect.Effects;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;

import static dundigundi.betterthanfarming.item.BetterThanFarmingItems.MOD_ID;

public class BetterThanFarmingEffects {
	public static final EffectRegeneration regenerationEffect = new EffectRegeneration("effect.betterthanfarming.regeneration",
			MOD_ID + ":regeneration",
			"assets/betterthanfarming/effects/regeneration/regeneration_vignette.png",
			0xFC0335,
			CatalystEffects.listOf(new Modifier[]{}), EffectTimeType.RESET, 240, 1);

	public void initializeEffects(){
		Effects.getInstance().register(regenerationEffect.id, regenerationEffect);
	}
}
