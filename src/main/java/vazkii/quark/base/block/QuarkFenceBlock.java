package vazkii.quark.base.block;

import java.util.function.BooleanSupplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.FenceBlock;
import vazkii.arl.util.RegistryHelper;
import vazkii.quark.base.module.QuarkModule;

public class QuarkFenceBlock extends FenceBlock implements IQuarkBlock {

	private final QuarkModule module;
	private BooleanSupplier enabledSupplier = () -> true;

	public QuarkFenceBlock(String regname, QuarkModule module, CreativeModeTab creativeTab, Properties properties) {
		super(properties);
		this.module = module;

		RegistryHelper.registerBlock(this, regname);
		if(creativeTab != null)
			RegistryHelper.setCreativeTab(this, creativeTab);
	}

	@Override
	public void fillItemCategory(@Nonnull CreativeModeTab group, @Nonnull NonNullList<ItemStack> items) {
		if(isEnabled() || (group == CreativeModeTab.TAB_SEARCH && appearInSearch()))
			super.fillItemCategory(group, items);
	}

	@Override
	public QuarkFenceBlock setCondition(BooleanSupplier enabledSupplier) {
		this.enabledSupplier = enabledSupplier;
		return this;
	}

	@Override
	public boolean doesConditionApply() {
		return enabledSupplier.getAsBoolean();
	}

	@Nullable
	@Override
	public QuarkModule getModule() {
		return module;
	}

}
