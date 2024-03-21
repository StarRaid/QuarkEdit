package vazkii.quark.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.quark.content.client.module.UsesForCursesModule;

@Mixin(Gui.class)
public class GuiMixin {

	@Inject(method = "renderTextureOverlay", at = @At("HEAD"), cancellable = true)
	public void changeArmorItem(ResourceLocation location, float alpha, CallbackInfo ci) {
		Player player = Minecraft.getInstance().player;
		if (player != null) {
			if (UsesForCursesModule.shouldHidePumpkinOverlay(location, player))
				ci.cancel();
		}
	}
}
