package vazkii.quark.content.building.module;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import vazkii.quark.base.block.QuarkGlassBlock;
import vazkii.quark.base.block.QuarkInheritedPaneBlock;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.ModuleCategory;
import vazkii.quark.base.module.QuarkModule;

@LoadModule(category = ModuleCategory.BUILDING)
public class FramedGlassModule extends QuarkModule {

	@Override
	public void register() {
		Block.Properties props = Block.Properties.of(Material.GLASS)
				.strength(3F, 10F)
				.sound(SoundType.GLASS);
		
		new QuarkInheritedPaneBlock(new QuarkGlassBlock("framed_glass", this, CreativeModeTab.TAB_BUILDING_BLOCKS, false, props));
		
		for(DyeColor dye : DyeColor.values())
			new QuarkInheritedPaneBlock(new QuarkGlassBlock(dye.getName() + "_framed_glass", this, CreativeModeTab.TAB_BUILDING_BLOCKS, true, props));
	}

}
