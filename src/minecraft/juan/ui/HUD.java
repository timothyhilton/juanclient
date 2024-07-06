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
		FontRenderer fr = mc.fontRendererObj;
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(4, 4, 0);
		GlStateManager.scale(1.5, 1.5, 1);
		GlStateManager.translate(-4, -4, 0);
		fr.drawStringWithShadow(Client.name + " " + Client.version, 4, 4, -1);
		GlStateManager.popMatrix();
		
		Client.onEvent(new EventRenderGUI());
	}
	
}
