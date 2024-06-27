package juan.modules.player;

import org.lwjgl.input.Keyboard;
import net.minecraft.network.play.client.C03PacketPlayer;
import juan.Client;
import juan.events.Event;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;

public class LegitMode extends Module {
	
	public LegitMode() {
		super("LegitMode", Keyboard.KEY_M, Category.PLAYER, true);
	}
	
	public void onEvent(Event e) {
		Client.modules.forEach(m -> {
			if(!m.legit && m.toggled)
				m.toggle();
		});
	}
	
}
