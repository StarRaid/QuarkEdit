package vazkii.quark.base.network.message;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import vazkii.arl.network.IMessage;

import java.io.Serial;

public class UpdateTridentMessage implements IMessage {

	@Serial
	private static final long serialVersionUID = -4716987873031723456L;

	public int tridentID;
	public ItemStack stack;

	public UpdateTridentMessage() { }

	public UpdateTridentMessage(int trident, ItemStack stack) {
		this.tridentID = trident;
		this.stack = stack;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean receive(NetworkEvent.Context context) {
		context.enqueueWork(() -> {
			Level level = Minecraft.getInstance().level;
			if (level != null) {
				Entity entity = level.getEntity(tridentID);
				if (entity instanceof ThrownTrident trident) {
					trident.tridentItem = stack;
				}
			}
		});

		return true;
	}

}
