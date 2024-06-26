package vazkii.quark.content.automation.module;

import net.minecraft.world.level.block.Block;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.ModuleCategory;
import vazkii.quark.base.module.QuarkModule;
import vazkii.quark.base.module.config.Config;
import vazkii.quark.base.module.hint.Hint;
import vazkii.quark.content.automation.block.MetalButtonBlock;

@LoadModule(category = ModuleCategory.AUTOMATION)
public class MetalButtonsModule extends QuarkModule {

	@Config(flag = "iron_metal_button")
	public static boolean enableIron = true;
	@Config(flag = "gold_metal_button")
	public static boolean enableGold = true;
	
	@Hint("iron_metal_button") Block iron_button;
	@Hint("gold_metal_button") Block gold_button;

	@Override
	public void register() {
		iron_button = new MetalButtonBlock("iron_button", this, 100).setCondition(() -> enableIron);
		gold_button = new MetalButtonBlock("gold_button", this, 4).setCondition(() -> enableGold);
	}
	
}
