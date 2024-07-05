package juan.modules.player;

import org.lwjgl.input.Keyboard;
import net.minecraft.network.play.client.C03PacketPlayer;
import juan.Client;
import juan.events.Event;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;

public class MouseDelayFix extends Module {
	
	public MouseDelayFix() {
		super("MouseDelayFix", Keyboard.KEY_NONE, Category.PLAYER, true);
	}
	
}
