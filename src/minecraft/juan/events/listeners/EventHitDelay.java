package juan.events.listeners;

import juan.events.Event;
import net.minecraft.client.Minecraft;

public class EventHitDelay extends Event<EventHitDelay> {

	public Minecraft minecraft;
	
	public EventHitDelay(Minecraft minecraft) {
		this.minecraft = minecraft;
	}
	
}
