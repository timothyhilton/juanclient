package juan.modules.render;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import juan.Client;
import juan.events.Event;
import juan.events.listeners.EventRenderGUI;
import juan.events.listeners.EventSwing;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class CPSCounter extends Module {
	
	private List<Long> clicks = new ArrayList<Long>();
	private int cps = 0;
	private String text;
	private FontRenderer fr = mc.fontRendererObj;

	public CPSCounter() {
		super("CPSCounter", Keyboard.KEY_NONE, Category.RENDER, true);
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if (e.isPre()) {
				long oneSecondAgo = System.currentTimeMillis() - 1000;
				
				while (!clicks.isEmpty() && (clicks.get(0) < oneSecondAgo)) {
		            clicks.remove(0);
		        }
			}
		}
		
		if(e instanceof EventSwing) {
			clicks.add(System.currentTimeMillis());
		}
		
		if(e instanceof EventRenderGUI) {
			int FPSCounterOffset = 0;
			
			for(Module m : Client.modules) {
				if(m.name == "FPSCounter" && m.toggled) {
					FPSCounterOffset = fr.FONT_HEIGHT + 7;
					continue;
				}
			}
			
			text = "CPS: " + String.valueOf(clicks.size());
			
			Gui.drawRect(1, 75 + FPSCounterOffset, fr.getStringWidth(text) + 6, 75 + 6 + fr.FONT_HEIGHT + FPSCounterOffset, 0x90000000);
			fr.drawStringWithShadow(text, 3.5f, 79 + FPSCounterOffset, -1);
		}
	}
}
