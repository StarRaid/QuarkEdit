package vazkii.quark.mixin;

import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import vazkii.quark.content.experimental.module.GameNerfsModule;

import java.util.function.Predicate;

@Mixin(ExperienceOrb.class)
public class ExperienceOrbMixin {

	@ModifyArg(method = "repairPlayerItems",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getRandomItemWith(Lnet/minecraft/world/item/enchantment/Enchantment;Lnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Predicate;)Ljava/util/Map$Entry;"))
	private Predicate<ItemStack> alterPredicateForMending(Predicate<ItemStack> predicate) {
		return GameNerfsModule.limitMendingItems(predicate);
	}

}
