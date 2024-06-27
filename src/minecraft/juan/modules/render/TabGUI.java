package juan.modules.render;

import java.util.List;

import org.lwjgl.input.Keyboard;

import juan.Client;
import juan.events.Event;
import juan.events.listeners.EventKey;
import juan.events.listeners.EventRenderGUI;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class TabGUI extends Module {
	
	public int currentTab;
	public boolean expanded;
	
	public TabGUI() {
		super("TabGUI", Keyboard.KEY_NONE, Category.RENDER, true);
		toggled = true;
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventRenderGUI) {
			FontRenderer fr = mc.fontRendererObj;
			
			// main box
			Gui.drawRect(1, 20, 53, 74, 0x90000000);
			
			// selected category box
			float tabOffset = currentTab * 13;
			Gui.drawRect(2, 21 + tabOffset, 52, 34 + tabOffset, 0x90000000);
			
			// category text
			int count = 0;
			for(Category c : Module.Category.values()) {
				int offset = count * 13;
				fr.drawStringWithShadow(c.name, 4, 24 + offset, -1);
				
				count++;
			}
			
			if(expanded) {
				Category category = Module.Category.values()[currentTab];
				List<Module> modules = Client.getModulesByCategory(category);
				
				count = 0;
				for(Module m : modules) {
					if(count == 0) {
						// main module box
						Gui.drawRect(54, 20, 61 + fr.getStringWidth(m.name), 21 + 13 * modules.size() + 1, 0x90000000);
						
						// selected module box
						float moduleOffset = category.moduleIndex * 13;
						Gui.drawRect(55, 21 + moduleOffset, 60 + fr.getStringWidth(m.name), 34 + moduleOffset, 0x90000000);
					}
					
					// module text
					int offset = count * 13;
					fr.drawStringWithShadow(m.name, 4 + 54, 24 + offset, -1);
					
					count++;
				}
				
				
			}
		}
		
		if(e instanceof EventKey) {
			Category category = Module.Category.values()[currentTab];
			List<Module> modules = Client.getModulesByCategory(category);
			int code = ((EventKey)e).code;
			
			if(code == Keyboard.KEY_UP) {
				if(expanded) {
					if(category.moduleIndex <= 0) {
						category.moduleIndex = modules.size() - 1;
					} else {
						category.moduleIndex--;
					}
				} else {
					if(currentTab <= 0) {
						currentTab = Module.Category.values().length -1;
					} else {
						currentTab--;
					}
				}
			}
			if(code == Keyboard.KEY_DOWN) {
				if(expanded) {
					if(category.moduleIndex >= modules.size() - 1) {
						category.moduleIndex = 0;
					} else {
						category.moduleIndex++;
					}
				} else {
					if(currentTab >= Module.Category.values().length - 1) {
						currentTab = 0;
					} else {
						currentTab++;
					}
				}
				
			}
			if(code == Keyboard.KEY_RIGHT) {
				if(expanded) {
					Module module = modules.get(category.moduleIndex);
					if(!(module.name == "TabGUI"))
						module.toggle();
				} else {
					expanded = true;
				}
			}
			if(code == Keyboard.KEY_LEFT) {
				expanded = false;
			}
		}
	}
	
}
