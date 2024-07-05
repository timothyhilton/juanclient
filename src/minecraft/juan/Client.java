package juan;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.opengl.Display;

import juan.events.Event;
import juan.events.listeners.*;
import juan.modules.Module;
import juan.modules.Module.Category;
import juan.modules.combat.*;
import juan.modules.movement.*;
import juan.modules.player.*;
import juan.modules.render.*;
import juan.ui.HUD;

public class Client {

	public static String name = "juan client", version = "b2.1.8";
	public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();
	public static HUD hud = new HUD();

	public static void startup() {
		System.out.println("Starting " + name + " " + version);
		Display.setTitle(name + " " + version);
		
		modules.add(new FPSCounter());
		modules.add(new OldSwing());
		
		modules.add(new Fly());
		modules.add(new Sprint());
		modules.add(new BlocksMCFireball());
		modules.add(new KillAura());
		modules.add(new Fullbright());
		modules.add(new NoFall());
		modules.add(new TabGUI());
		modules.add(new CopySession());
		modules.add(new CPSCounter());
		modules.add(new LegitMode());
		modules.add(new BedwarsItemCounter());
		modules.add(new HitDelayFix());
		modules.add(new MouseDelayFix());
	}
	
	public static void onEvent(Event e) {
		for(Module m : modules) {
			if(!m.toggled)
				continue;
			m.onEvent(e);
		}
	}
	
	public static void keyPress(int key) {
		Client.onEvent(new EventKey(key));
		
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
	
}
