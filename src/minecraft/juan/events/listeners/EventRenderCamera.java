package juan.events.listeners;

import juan.events.Event;

public class EventRenderCamera extends Event<EventRenderCamera> {
	
	public float yaw, pitch;
	
	public EventRenderCamera(float yaw, float pitch) {
		this.yaw = yaw;
		this.pitch = pitch;
	}
}
