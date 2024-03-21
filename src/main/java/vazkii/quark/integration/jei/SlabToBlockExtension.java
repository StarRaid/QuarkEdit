package vazkii.quark.integration.jei;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Nonnull;

import org.apache.commons.compress.utils.Lists;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import vazkii.quark.content.tweaks.module.SlabsToBlocksModule;
import vazkii.quark.content.tweaks.recipe.SlabToBlockRecipe;

public record SlabToBlockExtension(SlabToBlockRecipe recipe) implements ICraftingCategoryExtension {
	
	@Override
	public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull ICraftingGridHelper craftingGridHelper, @Nonnull IFocusGroup focuses) {
		List<ItemStack> input1 = Lists.newArrayList();
		List<ItemStack> input2 = Lists.newArrayList();
		List<ItemStack> outputs = Lists.newArrayList();
		
		for (Entry<Item, Item> recipe : SlabsToBlocksModule.recipes.entrySet()) {
			input1.add(new ItemStack(recipe.getKey()));
			input2.add(new ItemStack(recipe.getKey()));
			outputs.add(new ItemStack(recipe.getValue()));
		}

		List<IRecipeSlotBuilder> gridSlots = craftingGridHelper.createAndSetInputs(builder, Arrays.asList(input1, input2), 0, 0);
		IRecipeSlotBuilder outSlot = craftingGridHelper.createAndSetOutputs(builder, outputs);
		
		builder.createFocusLink(new IRecipeSlotBuilder[] {
				gridSlots.get(0), gridSlots.get(1), outSlot
		});
	}

	@Override
	public ResourceLocation getRegistryName() {
		return recipe.getId();
	}
}
