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
import juan.settings.BooleanSetting;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class FPSCounter extends Module {
	
	private FontRenderer fr = mc.fontRendererObj;
	private String text;
	BooleanSetting ignoreTheme = new BooleanSetting("Ignore Theme", false);

	public FPSCounter() {
		super("FPSCounter", Keyboard.KEY_NONE, Category.RENDER, true);
		this.addSettings(ignoreTheme);
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventRenderGUI) {
			text = "FPS: " + String.valueOf(mc.getDebugFPS());
			
			Gui.drawRect(1, 75, fr.getStringWidth(text) + 6, 75 + 6 + fr.FONT_HEIGHT, 0x90000000);
			fr.drawStringWithShadow(text, 3.5f, 79, ignoreTheme.isEnabled() ? -1 : Client.theme.getColour(5));
		}
	}
}
