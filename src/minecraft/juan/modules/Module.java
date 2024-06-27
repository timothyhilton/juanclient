package juan.modules;

import juan.events.Event;
import net.minecraft.client.Minecraft;

public class Module {
	
	public String name;
	public boolean toggled;
	public int keyCode;
	public Category category;
	public final boolean legit;
	public Minecraft mc = Minecraft.getMinecraft();
	
	public Module(String name, int key, Category c, boolean legit) {
		this.name = name;
		this.keyCode = key;
		this.category = c;
		this.legit = legit;
	}
	
	public boolean isEnabled() {
		return toggled;
	}
	
	public int getKey() {
		return keyCode;
	}
	
	public void onEvent(Event e) {
		
	}

	public void toggle() {
		toggled = !toggled;
		if(toggled) { onEnable(); }
		else { onDisable(); }
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable(){
		
	}
	
	public enum Category {
		COMBAT("Combat"),
		MOVEMENT("Movement"),
		PLAYER("Player"),
		RENDER("Render");
		
		public String name;
		public int moduleIndex;
		
		Category(String name){
			this.name = name;
		}
	}
	
}
