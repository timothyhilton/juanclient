package juan.events.listeners;

import juan.events.Event;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemRenderer;

public class EventHandleBlockHit extends Event<EventHandleBlockHit> {

	public ItemRenderer ir;
	public AbstractClientPlayer acs;
	public float partialTicks;
	public float swingProgress;
	public float f;
	
	public EventHandleBlockHit(ItemRenderer ir, AbstractClientPlayer acs, float partialTicks, float swingProgress, float f) {
		this.ir = ir;
		this.acs = acs;
		this.partialTicks = partialTicks;
		this.swingProgress = swingProgress;
		this.f = f;
	}
}
