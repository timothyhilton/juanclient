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

public class Theme extends Module {
	
	public ModeSetting theme = new ModeSetting("theme", "rainbow", "rainbow", "unsaturated rainbow");
	
	public Theme() {
		super("Theme", Keyboard.KEY_NONE, Category.RENDER, true);
		toggled = true;
		this.addSettings(theme);
	}
	
	public void onEnable() {
		toggle();
	}
	
	public int getColour(int index) {
		return 1;
	}
}
