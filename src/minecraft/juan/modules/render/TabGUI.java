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
import juan.settings.*;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class TabGUI extends Module {
	
	public int currentTab;
	public boolean expanded;
	
	public BooleanSetting OldNavigation = new BooleanSetting("OldNavigation", false);
	public BooleanSetting ignoreTheme = new BooleanSetting("Ignore Theme", false);
	
	public TabGUI() {
		super("TabGUI", Keyboard.KEY_NONE, Category.RENDER, true);
		toggled = true;
		this.addSettings(OldNavigation, ignoreTheme);
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventRenderGUI) {
			FontRenderer fr = mc.fontRendererObj;
			
			int 
				primaryColour = 0x90000000,
				secondaryColour = 0x30ffffff,
				focusedColour = 0x80ffffff;
			
			// main box
			Gui.drawRect(1, 20, 53, 74, primaryColour);
			
			// selected category box
			float tabOffset = currentTab * 13;
			Gui.drawRect(2, 21 + tabOffset, 52, 34 + tabOffset, secondaryColour);
			
			// category text
			int count = 0;
			for(Category c : Module.Category.values()) {
				int offset = count * 13;
				fr.drawStringWithShadow(c.name, 4, 24 + offset, ignoreTheme.isEnabled() ? -1 : Client.theme.getColour(count + 1));
				
				count++;
			}
			
			if(expanded) {
				Category category = Module.Category.values()[currentTab];
				List<Module> modules = Client.getModulesByCategory(category);
				
				count = 0;
				float moduleBoxRight = 0;
				float moduleBoxTop = 0;
				for(Module m : modules) {
					if(count == 0) {
						
						moduleBoxRight = 61 + fr.getStringWidth(m.name);
						
						// main module box
						Gui.drawRect(54, 20, moduleBoxRight, 21 + 13 * modules.size() + 1, primaryColour);
						
						// selected module box
						float moduleOffset = category.moduleIndex * 13;
						moduleBoxTop = moduleOffset + 21;
						Gui.drawRect(55, moduleBoxTop, 60 + fr.getStringWidth(m.name), 34 + moduleOffset, secondaryColour);
					} 
					
					// module text
					int offset = count * 13;
					fr.drawStringWithShadow(m.name, 4 + 54, 24 + offset, ignoreTheme.isEnabled() ? -1 : Client.theme.getColour(count + 1));
					
					// module settings
					if(count == category.moduleIndex && m.expanded) {
						
						double boxWidth = 0;
					    for(Setting setting : m.settings) {
					    	// setting text
					    	if(setting instanceof BooleanSetting) {
					    		double length = fr.getStringWidth(setting.name + ": " + (((BooleanSetting) setting).enabled ? "enabled" : "disabled"));
					    		
					    		if(length > boxWidth)
					    			boxWidth = length;
					    	}
					    	if(setting instanceof NumberSetting) {
					    		double length = fr.getStringWidth(setting.name + ": " + ((NumberSetting) setting).getValue());
					    		
					    		if(length > boxWidth)
					    			boxWidth = length;
					    	}
					    	if(setting instanceof ModeSetting) {
					    		double length = fr.getStringWidth(setting.name + ": " + ((ModeSetting) setting).getMode());
					    		
					    		if(length > boxWidth)
					    			boxWidth = length;
					    	}
					    }
					    
					    // main setting box
					    Gui.drawRect(moduleBoxRight + 1, 20, moduleBoxRight + 1 + boxWidth + 8, 21 + 13 * m.settings.size() + 1, primaryColour);
						
					    // selected setting box
					    Gui.drawRect(moduleBoxRight + 2, 21 + 13 * m.settingIndex, moduleBoxRight + 1 + boxWidth + 7, 21 + 13 + 13 * m.settingIndex, m.settings.get(m.settingIndex).focused ? focusedColour : secondaryColour);
					    
					    int i = 0;
					    for(Setting setting : m.settings) {
					    	// setting text
					    	if(setting instanceof BooleanSetting) {
					    		BooleanSetting bool = (BooleanSetting) setting;
					    		fr.drawStringWithShadow(setting.name + ": " + (bool.enabled ? "enabled" : "disabled"), moduleBoxRight + 5, 24 + i * 13, ignoreTheme.isEnabled() ? -1 : Client.theme.getColour(i + 1));
					    	}
					    	if(setting instanceof NumberSetting) {
					    		NumberSetting number = (NumberSetting) setting;
					    		fr.drawStringWithShadow(setting.name + ": " + number.getValue(), moduleBoxRight + 5, 24 + i * 13, ignoreTheme.isEnabled() ? -1 : Client.theme.getColour(i + 1));
					    	}
					    	if(setting instanceof ModeSetting) {
					    		ModeSetting mode = (ModeSetting) setting;
					    		fr.drawStringWithShadow(setting.name + ": " + mode.getMode(), moduleBoxRight + 5, 24 + i * 13, ignoreTheme.isEnabled() ? -1 : Client.theme.getColour(i + 1));
					    	}
					    	
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
			
			if (!OldNavigation.isEnabled()) {
				if (code == Keyboard.KEY_RIGHT) {
			        if (!expanded) {
			            expanded = true;
			        } else if (!modules.isEmpty()) {
			            Module selectedModule = modules.get(category.moduleIndex);
			            if (!selectedModule.expanded) {
			                if (!selectedModule.settings.isEmpty()) {
			                    selectedModule.expanded = true;
			                } else {
			                    // If the module has no settings, just toggle it
			                    selectedModule.toggle();
			                }
			            } else if (!selectedModule.settings.isEmpty()) {
			                Setting selectedSetting = selectedModule.settings.get(selectedModule.settingIndex);
			                selectedSetting.focused = true;
			            }
			        }
			    } else if (code == Keyboard.KEY_LEFT) {
			        if (expanded && !modules.isEmpty()) {
			            Module selectedModule = modules.get(category.moduleIndex);
			            if (selectedModule.expanded) {
			                if (selectedModule.settings.isEmpty() || !selectedModule.settings.get(selectedModule.settingIndex).focused) {
			                    selectedModule.expanded = false;
			                } else {
			                    selectedModule.settings.get(selectedModule.settingIndex).focused = false;
			                }
			            } else {
			                expanded = false;
			            }
			        }
			    } else if (code == Keyboard.KEY_UP) {
			        if (!expanded) {
			            currentTab = (currentTab - 1 + Module.Category.values().length) % Module.Category.values().length;
			        } else if (!modules.isEmpty()) {
			            Module selectedModule = modules.get(category.moduleIndex);
			            if (selectedModule.expanded && !selectedModule.settings.isEmpty()) {
			                Setting selectedSetting = selectedModule.settings.get(selectedModule.settingIndex);
			                if (selectedSetting.focused) {
			                    if (selectedSetting instanceof NumberSetting) {
			                        ((NumberSetting) selectedSetting).increment(true);
			                    } else if (selectedSetting instanceof BooleanSetting) {
			                        ((BooleanSetting) selectedSetting).toggle();
			                    } else if (selectedSetting instanceof ModeSetting) {
			                        ((ModeSetting) selectedSetting).cycle(true);
			                    }
			                } else {
			                    selectedModule.settingIndex = (selectedModule.settingIndex - 1 + selectedModule.settings.size()) % selectedModule.settings.size();
			                }
			            } else {
			                category.moduleIndex = (category.moduleIndex - 1 + modules.size()) % modules.size();
			            }
			        }
			    } else if (code == Keyboard.KEY_DOWN) {
			        if (!expanded) {
			            currentTab = (currentTab + 1) % Module.Category.values().length;
			        } else if (!modules.isEmpty()) {
			            Module selectedModule = modules.get(category.moduleIndex);
			            if (selectedModule.expanded && !selectedModule.settings.isEmpty()) {
			                Setting selectedSetting = selectedModule.settings.get(selectedModule.settingIndex);
			                if (selectedSetting.focused) {
			                    if (selectedSetting instanceof NumberSetting) {
			                        ((NumberSetting) selectedSetting).increment(false);
			                    } else if (selectedSetting instanceof BooleanSetting) {
			                        ((BooleanSetting) selectedSetting).toggle();
			                    } else if (selectedSetting instanceof ModeSetting) {
			                        ((ModeSetting) selectedSetting).cycle(false);
			                    }
			                } else {
			                    selectedModule.settingIndex = (selectedModule.settingIndex + 1) % selectedModule.settings.size();
			                }
			            } else {
			                category.moduleIndex = (category.moduleIndex + 1) % modules.size();
			            }
			        }
			    } else if (code == Keyboard.KEY_RETURN) {
			        if (expanded && !modules.isEmpty()) {
			            Module selectedModule = modules.get(category.moduleIndex);
			            if (!selectedModule.expanded || selectedModule.settings.isEmpty()) {
			                selectedModule.toggle();
			            }
			        }
			    }
				
			} else {
				if(code == Keyboard.KEY_UP) {
					if(expanded) {
						if(expanded && !modules.isEmpty() && modules.get(category.moduleIndex).expanded) {
							Module module = modules.get(category.moduleIndex);
							Setting setting = module.settings.get(module.settingIndex);
							
							if(setting.focused) {
								if(setting instanceof NumberSetting) {
									((NumberSetting) setting).increment(true);
								} else if (setting instanceof BooleanSetting) {
									((BooleanSetting) setting).toggle();
								} else if (setting instanceof ModeSetting) {
									((ModeSetting) setting).cycle(true);
								}
								
							} else {
								if(module.settingIndex <= 0) {
									module.settingIndex = module.settings.size() - 1;
								} else {
									module.settingIndex--;
								}
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
							Setting setting = module.settings.get(module.settingIndex);
							if(setting.focused) {
								if(setting instanceof NumberSetting) {
									((NumberSetting) setting).increment(false);
								} else if (setting instanceof BooleanSetting) {
									((BooleanSetting) setting).toggle();
								} else if (setting instanceof ModeSetting) {
									((ModeSetting) setting).cycle(false);
								}
							} else {
								if(module.settingIndex >= module.settings.size() - 1) {
									module.settingIndex = 0;
								} else
									module.settingIndex ++;							
							}
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
						
						if(expanded && !modules.isEmpty() && !module.settings.isEmpty() && modules.get(category.moduleIndex).expanded) {
							Setting setting = module.settings.get(module.settingIndex);
							setting.focused = !setting.focused;
							
						} else {
							if(!(module.name == "TabGUI"))
								module.toggle();
						}
						
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
						
						if(module.settings.isEmpty())
							return;
						
						module.expanded = !module.expanded;
					}
				}
			}
		}
	}
	
}
