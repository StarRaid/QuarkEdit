package vazkii.quark.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BeehiveBlockEntity.class)
public class BeehiveBlockEntityMixin {

	@WrapWithCondition(method = {"writeBees", "removeIgnoredBeeTags"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;remove(Ljava/lang/String;)V"))
	private static boolean doNotRemoveUUIDOfBees(CompoundTag instance, String key) {
		return !key.equals("UUID");
	}


	@Inject(method = "setBeeReleaseData", at = @At("HEAD"))
	private static void rerollUUIDIfNeeded(int ticksInHive, Bee bee, CallbackInfo ci) {
		if (bee.level instanceof ServerLevel level) {
			if (level.getEntities().get(bee.getUUID()) != null)
				bee.setUUID(Mth.createInsecureUUID(level.random));
		}
	}

}
