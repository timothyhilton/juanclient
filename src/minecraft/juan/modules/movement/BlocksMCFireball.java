package juan.modules.movement;

import org.lwjgl.input.Keyboard;

import juan.events.Event;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;
import juan.modules.Module.Category;

public class BlocksMCFireball extends Module {
	
	float startingHealth;
	
	public BlocksMCFireball() {
		super("BlocksMCFireball", Keyboard.KEY_B, Category.MOVEMENT, false);
	}
	
	public void onEnable() {
		startingHealth = mc.thePlayer.getHealth();
	}
	
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			if(e.isPre()) {

				if(mc.thePlayer.getHealth() < startingHealth) {
					mc.thePlayer.addVelocity(0, 1.5, 0);
					toggle();
				}
				
				if(mc.thePlayer.getHealth() > startingHealth)
					startingHealth = mc.thePlayer.getHealth();
				
			}
		}
	}
}
