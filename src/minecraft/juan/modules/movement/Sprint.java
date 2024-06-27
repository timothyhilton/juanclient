package juan.modules.movement;

import org.lwjgl.input.Keyboard;

import juan.events.Event;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;

public class Sprint extends Module {
	
	public Sprint() {
		super("Sprint", Keyboard.KEY_N, Category.MOVEMENT, true);
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable(){
		mc.thePlayer.setSprinting(mc.gameSettings.keyBindSprint.isPressed());
	}
	
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			if(e.isPre()) {
				if(mc.thePlayer.moveForward > 0 && !mc.thePlayer.isUsingItem() && !mc.thePlayer.isSneaking() && !mc.thePlayer.isCollidedHorizontally && mc.thePlayer.getFoodStats().getFoodLevel() > 6) {
					mc.thePlayer.setSprinting(true);
				}
			}
		}
	}
	
}
