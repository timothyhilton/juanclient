package juan.modules.render;
import java.awt.Color;
import java.util.Comparator;

import org.lwjgl.input.Keyboard;

import juan.Client;
import juan.events.Event;
import juan.events.listeners.EventHandleBlockHit;
import juan.events.listeners.EventRenderGUI;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;
import juan.settings.BooleanSetting;
import juan.settings.ModeSetting;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class ArrayListModule extends Module {
	
	BooleanSetting compact = new BooleanSetting("Compact", false);
	BooleanSetting background = new BooleanSetting("Background", true);
	BooleanSetting edgeGap = new BooleanSetting("EdgeGap", false);
	BooleanSetting spaced = new BooleanSetting("Spaced", false);
	BooleanSetting ignoreTheme = new BooleanSetting("Ignore Theme", false);
	
	public ArrayListModule() {
		super("ArrayList", Keyboard.KEY_NONE, Category.RENDER, true);
		toggled = true;
		this.addSettings(compact, background, edgeGap, spaced, ignoreTheme);
	}
	
	public void onEnable() {
	}
	
	public void onDisable(){
	}
	
	public void onEvent(Event e){
		if(!(e instanceof EventRenderGUI))
			return;
		
		ScaledResolution sr = new ScaledResolution(mc);
		FontRenderer fr = mc.fontRendererObj;
		
		
		Client.modules.sort(Comparator.comparingInt(m -> fr.getStringWidth(((Module)m).name)).reversed());
		
		int count = 0;
		for(Module m : Client.modules) {
			if(!m.toggled || m.name == "TabGUI")
				continue;
			
			double offset = count * (fr.FONT_HEIGHT + (compact.enabled ? 2 : 6) + (spaced.enabled ? 1 : 0)) - (compact.enabled ? 2 : 0) + (edgeGap.enabled ? 1 : 0);

			int colour = Client.theme.getColour(count);
			if(ignoreTheme.isEnabled())
				colour = -1;
	        count++;
			
			if(background.enabled)
				Gui.drawRect(
						sr.getScaledWidth() - fr.getStringWidth(m.name) - 8 + (compact.enabled ? 5 : 0) - (edgeGap.enabled ? 1 : 0), 
						offset + (compact.enabled ? 2 : 0), 
						sr.getScaledWidth() - (edgeGap.enabled ? 1 : 0), 
						6 + fr.FONT_HEIGHT + offset - (compact.enabled ? 2 : 0), 
						0x90000000
				);
			
			fr.drawStringWithShadow(m.name, sr.getScaledWidth() - fr.getStringWidth(m.name) - 4 + (compact.enabled ? 3 : 0) - (edgeGap.enabled ? 1 : 0), 4 + offset, colour);
			
		}
	}
	
}