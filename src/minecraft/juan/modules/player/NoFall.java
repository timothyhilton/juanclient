package juan.modules.player;

import org.lwjgl.input.Keyboard;
import net.minecraft.network.play.client.C03PacketPlayer;

import juan.events.Event;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;

public class NoFall extends Module {
	
	public NoFall() {
		super("NoFall", Keyboard.KEY_M, Category.PLAYER, false);
	}	
	
	public void onEvent(Event e) {
		if (e instanceof EventUpdate && e.isPre()) {
			if(mc.thePlayer.fallDistance > 2) {
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
			}
		}
	}
	
}
