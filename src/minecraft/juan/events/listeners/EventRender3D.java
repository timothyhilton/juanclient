package juan.events.listeners;

import juan.events.Event;

public class EventRender3D extends Event<EventRender3D> {

	private float partialTicks;
	
	public EventRender3D(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
