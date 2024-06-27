package juan.ui;

import java.util.Collections;
import java.util.Comparator;

import juan.Client;
import juan.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import juan.events.listeners.EventRenderGUI;

public class HUD {
	
	public Minecraft mc = Minecraft.getMinecraft();
	
	public void draw() {
		ScaledResolution sr = new ScaledResolution(mc);
		FontRenderer fr = mc.fontRendererObj;
		
		Client.modules.sort(Comparator.comparingInt(m -> mc.fontRendererObj.getStringWidth(((Module)m).name)).reversed());
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(4, 4, 0);
		GlStateManager.scale(1.5, 1.5, 1);
		GlStateManager.translate(-4, -4, 0);
		fr.drawStringWithShadow(Client.name + " " + Client.version, 4, 4, -1);
		GlStateManager.popMatrix();
		
		int count = 0;
		
		for(Module m : Client.modules) {
			if(!m.toggled || m.name == "TabGUI")
				continue;
			
			double offset = count * (fr.FONT_HEIGHT + 6);
			
			Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.name) - 8, offset, sr.getScaledWidth(), 6 + fr.FONT_HEIGHT + offset, 0x90000000);
			fr.drawStringWithShadow(m.name, sr.getScaledWidth() - fr.getStringWidth(m.name) - 4, 4 + offset, -1);
			
			count++;
		}
		
		Client.onEvent(new EventRenderGUI());
	}
	
}
