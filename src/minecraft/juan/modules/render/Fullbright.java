package juan.modules.render;

import org.lwjgl.input.Keyboard;

import juan.events.Event;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;

public class Fullbright extends Module {
	
	public Fullbright() {
		super("Fullbright", Keyboard.KEY_O, Category.RENDER, true);
	}
	
	public void onEnable() {
		mc.gameSettings.gammaSetting = 100;
	}
	
	public void onDisable(){
		mc.gameSettings.gammaSetting = 1;
	}
	
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			if(e.isPre()) {
				
			}
		}
	}
	
}
