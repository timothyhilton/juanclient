package juan.modules.render;

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
import juan.settings.Setting;
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
					int moduleBoxRight = 0;
					if(count == 0) {
						
						moduleBoxRight = 61 + fr.getStringWidth(m.name);
						// main module box
						Gui.drawRect(54, 20, moduleBoxRight, 21 + 13 * modules.size() + 1, 0x90000000);
						
						// selected module box
						float moduleOffset = category.moduleIndex * 13;
						Gui.drawRect(55, 21 + moduleOffset, 60 + fr.getStringWidth(m.name), 34 + moduleOffset, 0x90000000);
					}
					
					// module text
					int offset = count * 13;
					fr.drawStringWithShadow(m.name, 4 + 54, 24 + offset, -1);
					
					// module settings
					if(count == category.moduleIndex && m.expanded) {
						
						double boxWidth = 0;
						
					    for(Setting setting : m.settings) {
					        double width = mc.fontRendererObj.getStringWidth(setting.name);
					        if (width > boxWidth) {
					            boxWidth = width;
					        }
					    }
					    
					    // main setting box
					    Gui.drawRect(moduleBoxRight + 1, 20, moduleBoxRight + 1 + boxWidth + 5, 21 + 13 * m.settings.size() + 1, 0x90000000);
						
					    // selected setting box
					    Gui.drawRect(moduleBoxRight + 2, 21, moduleBoxRight + 1 + boxWidth + 4, 21 + 13, 0x90000000);
					    
					    int i = 0;
					    for(Setting setting : m.settings) {
					    	// setting text
					    	fr.drawStringWithShadow(setting.name, moduleBoxRight + 4, 24 + i * 13, -1);
					    	
					    	i++;
					    }
					}
					
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
					if(expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded) {
						Module module = modules.get(category.moduleIndex);
						if(module.settingIndex <= 0) {
							module.settingIndex = module.settings.size() - 1;
						} else {
							module.settingIndex--;
						}
					} else {
						if(category.moduleIndex <= 0) {
							category.moduleIndex = modules.size() - 1;
						} else {
							category.moduleIndex--;
						}
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
					
					// if settings menu is open
					if(expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded) {
						Module module = modules.get(category.moduleIndex);
						if(module.settingIndex >= module.settings.size() - 1) {
							module.settingIndex = 0;
						} else
							module.settingIndex ++;
					} else {
						if(category.moduleIndex >= modules.size() - 1) {
							category.moduleIndex = 0;
						} else {
							category.moduleIndex++;
						}
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
				if(expanded && modules.isEmpty() && modules.get(category.moduleIndex).expanded) {
					modules.get(category.moduleIndex).expanded = false;
				} else {
					expanded = false;
				}
			}
			
			if(code == Keyboard.KEY_RETURN) {
				if(expanded) {
					Module module = modules.get(category.moduleIndex);
					
					module.expanded = !module.expanded;
				}
			}
		}
	}
	
}
