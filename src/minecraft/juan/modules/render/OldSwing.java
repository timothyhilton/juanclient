package juan.modules.render;

import org.lwjgl.input.Keyboard;

import juan.events.Event;
import juan.events.listeners.EventHandleBlockHit;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;
import net.minecraft.client.renderer.GlStateManager;

public class OldSwing extends Module {
	
	public OldSwing() {
		super("OldSwing", Keyboard.KEY_NONE, Category.RENDER, true);
	}
	
	public void onEnable() {
	}
	
	public void onDisable(){
	}
	
	public void onEvent(Event e) {
		if (e instanceof EventHandleBlockHit) {
			EventHandleBlockHit event = ((EventHandleBlockHit) e);
			
            event.ir.transformFirstPersonItem(event.f, event.swingProgress);
            event.ir.func_178103_d();
            
            GlStateManager.rotate(-18.0F, 0.0F, 0.0F, 1.0F);
		}
	}
	
}
