package juan;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.opengl.Display;
import org.lwjgl.input.Keyboard;

import juan.command.CommandManager;
import juan.events.Event;
import juan.events.listeners.*;
import juan.modules.Module;
import juan.modules.Module.Category;
import juan.modules.combat.*;
import juan.modules.movement.*;
import juan.modules.player.*;
import juan.modules.render.*;
import juan.ui.HUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.util.ChatComponentText;

public class Client {

	public static String name = "juan client", version = "b2.2.1";
	public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();
	public static HUD hud = new HUD();
	public static CommandManager commandManager = new CommandManager();

	public static void startup() {
		System.out.println("Starting " + name + " " + version);
		Display.setTitle(name + " " + version);
		
		modules.add(new FPSCounter());
		modules.add(new OldSwing());
		
		modules.add(new Sprint());
		modules.add(new Fullbright());
		modules.add(new TabGUI());
		modules.add(new CopySession());
		modules.add(new CPSCounter());
		modules.add(new BedwarsItemCounter());
		modules.add(new HitDelayFix());
		modules.add(new MouseDelayFix());
		modules.add(new ArrayListModule());
		modules.add(new FreeLook());
		modules.add(new Theme());
	}
	
	public static void onEvent(Event e) {
		if(e instanceof EventChat) {
			commandManager.handleChat((EventChat) e);
		}
		
		
		for(Module m : modules) {
			if(!m.toggled)
				continue;
			m.onEvent(e);
		}
	}
	
	public static void keyPress(int key) {
		Client.onEvent(new EventKey(key));
		
		if(key == Keyboard.KEY_PERIOD) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiChat("."));
		}
		
		for(Module m : modules) {
			if(m.getKey() == key) {
				m.toggle();
			}
		}
	}
	
	public static List<Module> getModulesByCategory(Category c){
		List<Module> modules = new ArrayList<Module>();
		
		for(Module m : Client.modules) {
			if(m.category == c)
				modules.add(m);
		}
		
		return modules;
	}
	
	public static Module getModuleByName(String name) {
		for(Module m : modules) {
			if(m.name == name)
				return m;
		}
		return null;
	}
	
	public static CommandManager getCommandManager() {
		return commandManager;
	}
}
