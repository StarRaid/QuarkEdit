package vazkii.quark.base.network.message;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkEvent;
import vazkii.arl.network.IMessage;
import vazkii.quark.content.tweaks.module.SimpleHarvestModule;

import java.io.Serial;

public class HarvestMessage implements IMessage {

	@Serial
	private static final long serialVersionUID = -51788488328591145L;

	public BlockPos pos;
	public InteractionHand hand;

	public HarvestMessage() { }

	public HarvestMessage(BlockPos pos, InteractionHand hand) {
		this.pos = pos;
		this.hand = hand;
	}

	@Override
	public boolean receive(NetworkEvent.Context context) {
		context.enqueueWork(() -> {
			Player player = context.getSender();
			if (player != null) {
				BlockHitResult pick = Item.getPlayerPOVHitResult(player.getLevel(), player, ClipContext.Fluid.ANY);
				SimpleHarvestModule.click(context.getSender(), hand, pos, pick);
			}
		});
		return true;
	}

}
