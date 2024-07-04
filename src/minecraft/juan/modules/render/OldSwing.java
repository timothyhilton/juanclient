package juan.modules.render;

import org.lwjgl.input.Keyboard;

import juan.events.Event;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;

public class OldSwing extends Module {
	
	public OldSwing() {
		super("OldSwing", Keyboard.KEY_NONE, Category.RENDER, true);
	}
	
	public void onEnable() {
	}
	
	public void onDisable(){
	}
	
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			if(e.isPre()) {
				
			}
		}
	}
	
}
