package vazkii.quark.base.network.message;

import net.minecraftforge.network.NetworkEvent;
import vazkii.arl.network.IMessage;
import vazkii.quark.content.management.module.ExpandedItemInteractionsModule;

import java.io.Serial;

public class ScrollOnBundleMessage implements IMessage {

	@Serial
	private static final long serialVersionUID = 5598418693967300303L;

	public int containerId;
	public int stateId;
	public int slotNum;
	public double scrollDelta;

	public ScrollOnBundleMessage() {}

	public ScrollOnBundleMessage(int containerId, int stateId, int slotNum, double scrollDelta) {
		this.containerId = containerId;
		this.stateId = stateId;
		this.slotNum = slotNum;
		this.scrollDelta = scrollDelta;
	}

	@Override
	public boolean receive(NetworkEvent.Context context) {
		context.enqueueWork(() -> ExpandedItemInteractionsModule.scrollOnBundle(context.getSender(), containerId, stateId, slotNum, scrollDelta));
		return true;
	}

}
