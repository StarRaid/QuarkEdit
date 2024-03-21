package vazkii.quark.base.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface SimpleFluidloggedBlock extends BucketPickup, LiquidBlockContainer {
	
	@Override
	default boolean canPlaceLiquid(@Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull Fluid fluid) {
		return fluidContained(state) == Fluids.EMPTY && acceptsFluid(fluid);
	}

	@Override
	default boolean placeLiquid(@Nonnull LevelAccessor level, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull FluidState fluid) {
		if (canPlaceLiquid(level, pos, state, fluid.getType())) {
			if (!level.isClientSide()) {
				level.setBlock(pos, withFluid(state, fluid.getType()), 3);
				level.scheduleTick(pos, fluid.getType(), fluid.getType().getTickDelay(level));
			}

			return true;
		} else
			return false;
	}

	@Nonnull
	@Override
	default ItemStack pickupBlock(@Nonnull LevelAccessor level, @Nonnull BlockPos pos, @Nonnull BlockState state) {
		Fluid fluid = fluidContained(state);
		if (fluid != Fluids.EMPTY && fluid.getBucket() != Items.AIR) {
			level.setBlock(pos, withFluid(state, Fluids.EMPTY), 3);
			if (!state.canSurvive(level, pos))
				level.destroyBlock(pos, true);

			return new ItemStack(fluid.getBucket());
		} else
			return ItemStack.EMPTY;
	}

	@Nonnull
	@Override
	default Optional<SoundEvent> getPickupSound() {
		return Optional.empty(); // Irrelevant - using state variant below
	}

	@Override
	default Optional<SoundEvent> getPickupSound(BlockState state) {
		return fluidContained(state).getPickupSound();
	}

	boolean acceptsFluid(@Nonnull Fluid fluid);

	@Nonnull
	BlockState withFluid(@Nonnull BlockState state, @Nonnull Fluid fluid);

	@Nonnull
	Fluid fluidContained(@Nonnull BlockState state);

}
