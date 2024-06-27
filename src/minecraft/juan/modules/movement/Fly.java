package juan.modules.movement;

import org.lwjgl.input.Keyboard;

import juan.events.Event;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;

public class Fly extends Module {
	
	public Fly() {
		super("Fly", Keyboard.KEY_G, Category.MOVEMENT, false);
	}
	
	public void onDisable(){
		mc.thePlayer.capabilities.isFlying = false;
	}
	
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			if(e.isPre()) {
				mc.thePlayer.capabilities.isFlying = true;
			}
		}
	}
	
}
