package vazkii.quark.base.handler.advancement.mod;

import java.util.Set;
import java.util.function.BooleanSupplier;

import com.google.common.collect.ImmutableSet;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.EffectsChangedTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import vazkii.quark.api.IMutableAdvancement;
import vazkii.quark.base.handler.advancement.AdvancementModifier;
import vazkii.quark.base.module.QuarkModule;

public class FuriousCocktailModifier extends AdvancementModifier {
	
	private static final ResourceLocation TARGET_AP = new ResourceLocation("nether/all_potions");
	private static final ResourceLocation TARGET_AE = new ResourceLocation("nether/all_effects");

	final BooleanSupplier isPotion;
	final Set<MobEffect> effects;
	
	public FuriousCocktailModifier(QuarkModule module, BooleanSupplier isPotion, Set<MobEffect> effects) {
		super(module);
		
		this.isPotion = isPotion;
		this.effects = effects;
	}

	@Override
	public Set<ResourceLocation> getTargets() {
		return ImmutableSet.of(TARGET_AP, TARGET_AE);
	}

	@Override
	public boolean apply(ResourceLocation res, IMutableAdvancement adv) {
		if(!isPotion.getAsBoolean() && res.equals(TARGET_AP))
			return false;
		
		Criterion crit = adv.getCriterion("all_effects");
		if(crit != null && crit.getTrigger() instanceof EffectsChangedTrigger.TriggerInstance ect)  {
			for(MobEffect e : effects)
				ect.effects.and(e);
			
			return true;
		}
		
		return false;
	}

}
