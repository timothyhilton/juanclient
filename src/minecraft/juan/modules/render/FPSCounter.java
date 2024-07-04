package juan.modules.render;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import juan.events.Event;
import juan.events.listeners.EventRenderGUI;
import juan.events.listeners.EventSwing;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class FPSCounter extends Module {
	
	private FontRenderer fr = mc.fontRendererObj;
	private String text;

	public FPSCounter() {
		super("FPSCounter", Keyboard.KEY_NONE, Category.RENDER, true);
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventRenderGUI) {
			text = "FPS: " + String.valueOf(mc.getDebugFPS());
			
			Gui.drawRect(1, 75, fr.getStringWidth(text) + 6, 75 + 6 + fr.FONT_HEIGHT, 0x90000000);
			fr.drawStringWithShadow(text, 3.5f, 79, -1);
		}
	}
}
