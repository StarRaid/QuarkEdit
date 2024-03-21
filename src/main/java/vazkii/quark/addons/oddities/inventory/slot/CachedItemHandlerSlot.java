package vazkii.quark.addons.oddities.inventory.slot;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class CachedItemHandlerSlot extends SlotItemHandler {
	public CachedItemHandlerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}

	@Nonnull
	@Override
	public ItemStack getItem() {
		if (caching)
			return cached;
		return super.getItem();
	}

	@Nonnull
	@Override
	public ItemStack remove(int amount) {
		if (caching) {
			ItemStack newStack = cached.copy();
			int trueAmount = Math.min(amount, cached.getCount());
			cached.shrink(trueAmount);
			newStack.setCount(trueAmount);
			return newStack;
		}
		return super.remove(amount);
	}

	@Override
	public void set(@Nonnull ItemStack stack) {
		super.set(stack);
		if (caching)
			cached = stack;
	}

	private ItemStack cached = ItemStack.EMPTY;

	private boolean caching = false;

	public static void cache(AbstractContainerMenu container) {
		for (Slot slot : container.slots) {
			if (slot instanceof CachedItemHandlerSlot thisSlot) {
				thisSlot.cached = slot.getItem();
				thisSlot.caching = true;
			}
		}
	}

	public static void applyCache(AbstractContainerMenu container) {
		for (Slot slot : container.slots) {
			if (slot instanceof CachedItemHandlerSlot thisSlot) {
				if (thisSlot.caching) {
					slot.set(thisSlot.cached);
					thisSlot.caching = false;
				}
			}
		}
	}
}
