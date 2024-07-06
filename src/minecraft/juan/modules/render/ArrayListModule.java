package juan.modules.render;
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
	BooleanSetting noBackground = new BooleanSetting("NoBackground", false);
	
	public ArrayListModule() {
		super("ArrayList", Keyboard.KEY_NONE, Category.RENDER, true);
		toggled = true;
		this.addSettings(compact, noBackground);
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
			
			double offset = count * (fr.FONT_HEIGHT + (compact.enabled ? 2 : 6)) - (compact.enabled ? 2 : 0);
			count++;
			
			if(!noBackground.enabled)
				Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.name) - 8 + (compact.enabled ? 5 : 0), offset + (compact.enabled ? 2 : 0), sr.getScaledWidth(), 6 + fr.FONT_HEIGHT + offset - (compact.enabled ? 2 : 0), 0x90000000);
			
			fr.drawStringWithShadow(m.name, sr.getScaledWidth() - fr.getStringWidth(m.name) - 4 + (compact.enabled ? 3 : 0), 4 + offset, -1);
			
		}
	}
	
}