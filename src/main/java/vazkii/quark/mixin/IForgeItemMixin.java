package vazkii.quark.mixin;

import net.minecraftforge.common.extensions.IForgeItem;
import org.spongepowered.asm.mixin.Mixin;
import vazkii.quark.mixinsupport.DelegateInterfaceMixin;
import vazkii.quark.mixinsupport.DelegateReturnValueModifier;
import vazkii.quark.mixinsupport.delegates.ForgeItemDelegate;

@Mixin(IForgeItem.class)
@DelegateInterfaceMixin(delegate = ForgeItemDelegate.class, methods = {
	@DelegateReturnValueModifier(target = "getEnchantmentLevel(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/enchantment/Enchantment;)I",
		delegate = "getEnchantmentLevel", desc = "(ILnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/enchantment/Enchantment;)I"),
	@DelegateReturnValueModifier(target = "getAllEnchantments(Lnet/minecraft/world/item/ItemStack;)Ljava/util/Map;",
		delegate = "getAllEnchantments", desc = "(Ljava/util/Map;Lnet/minecraft/world/item/ItemStack;)Ljava/util/Map;")
})
public interface IForgeItemMixin {
	// Delegated. Only valid because IForgeItem members are not refmapped.
}
