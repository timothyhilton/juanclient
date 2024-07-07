package juan.modules.render;

import org.lwjgl.input.Keyboard;

import juan.events.Event;
import juan.events.listeners.EventHandleBlockHit;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;
import juan.settings.ModeSetting;
import net.minecraft.client.renderer.GlStateManager;

public class OldSwing extends Module {
	
	ModeSetting swingType = new ModeSetting("Type", "1.7ish", "1.7ish", "test", "high");
	
	public OldSwing() {
		super("OldSwing", Keyboard.KEY_NONE, Category.RENDER, true);
		this.addSettings(swingType);
	}
	
	public void onEnable() {
	}
	
	public void onDisable(){
	}
	
	public void onEvent(Event e) {
		if (e instanceof EventHandleBlockHit) {
			EventHandleBlockHit event = ((EventHandleBlockHit) e);
			
			switch(swingType.getMode()) {
				case "1.7ish":
					event.ir.transformFirstPersonItem(event.f, event.swingProgress);
		            event.ir.func_178103_d();
		            
		            GlStateManager.rotate(-18.0F, 0.0F, 0.0F, 1.0F);
		            
		            break;
				case "test":
					event.ir.transformFirstPersonItem(event.f, event.swingProgress);
		            
					GlStateManager.translate(-0.5F, 0.F, 0.0F);
			        GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
			        GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
			        GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
		            GlStateManager.rotate(-18.0F, 0.0F, 0.0F, 1.0F);
		            
		            break;
				case "high":
					event.ir.transformFirstPersonItem(event.f, event.swingProgress);
		            
					GlStateManager.translate(-0.5F, 0.8F, 0.0F);
			        GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
			        GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
			        GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
		            GlStateManager.rotate(-18.0F, 0.0F, 0.0F, 1.0F);
		            
		            break;
			}
			
            
		}
	}
	
}
