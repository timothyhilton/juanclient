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
	
	public ModeSetting theme = new ModeSetting("theme", "rainbow", "rainbow", "unsaturated rainbow", "white", "green-blue gradient");
	public BooleanSetting includeBadge = new BooleanSetting("include badge", false);
	public NumberSetting speed = new NumberSetting("speed", 1, 0, 3.5, 0.1);
	public NumberSetting smoothness = new NumberSetting("smoothness", 1, 0, 2, 0.1);
	
	public Theme() {
		super("Theme", Keyboard.KEY_NONE, Category.RENDER, true);
		this.addSettings(theme, includeBadge, speed, smoothness);
	}
	
	public void onEnable() {
		toggle();
	}
	
	public int getColour(int index) {
	    float timeAdjust = (float) ((System.currentTimeMillis() % (4000 / speed.getValue())) / (4000f / speed.getValue()));
	    float hue;
	    
	    switch(theme.getMode()) {
	        case "white":
	            return -1;
	            
	        case "rainbow":
	            timeAdjust += (index) * 0.05f / smoothness.getValue();
	            timeAdjust %= 1.0f;
	            return Color.HSBtoRGB(timeAdjust, 1f, 1);
	            
	        case "unsaturated rainbow":
	            timeAdjust += index * 0.05f / smoothness.getValue();
	            timeAdjust %= 1.0f;
	            return Color.HSBtoRGB(timeAdjust, 0.6f, 1);
	        
	        case "green-blue gradient":
	            float startHue = 120f / 360f;
	            float endHue = 200f / 360f;
	            float hueRange = endHue - startHue;
	            
	            float wrappedTime = (float) ((timeAdjust + index * 0.05f / smoothness.getValue()) % 1f);
	            
	            float sineWave = (float) Math.sin(wrappedTime * Math.PI);
	            hue = startHue + (hueRange * (sineWave + 1) / 2);
	            
	            float saturation = 0.8f;
	            float brightness = 1f;
	            
	            return Color.HSBtoRGB(hue, saturation, brightness);
	            
	    }
	    return -1;
	}
}
