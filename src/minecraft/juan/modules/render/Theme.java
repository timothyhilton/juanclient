package juan.modules.render;

import java.awt.Color;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.lwjgl.input.Keyboard;

import juan.Client;
import juan.events.Event;
import juan.events.listeners.EventKey;
import juan.events.listeners.EventRenderGUI;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;
import juan.settings.*;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class Theme extends Module {
	
	public ModeSetting theme = new ModeSetting("theme", "rainbow", "rainbow", "unsaturated rainbow", "white");
	
	public Theme() {
		super("Theme", Keyboard.KEY_NONE, Category.RENDER, true);
		toggled = true;
		this.addSettings(theme);
	}
	
	public void onEnable() {
		toggle();
	}
	
	public int getColour(int index) {
		float hue;
		
		switch(theme.getMode()) {
			case "white":
				return -1;
				
			case "rainbow":
				hue = (System.currentTimeMillis() % 4000) / 4000f;
				hue += index * 0.05f;
				hue %= 1.0f;
				
				return(Color.HSBtoRGB(hue, 1f, 1));
				
			case "unsaturated rainbow":
				hue = (System.currentTimeMillis() % 4000) / 4000f;
				hue += index * 0.05f;
				hue %= 1.0f;
				
				return(Color.HSBtoRGB(hue, 0.5f, 1));
			
			
				
		}
		return -1;
	}
}
