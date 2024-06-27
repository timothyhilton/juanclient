package juan.modules.render;

import org.lwjgl.input.Keyboard;

import juan.events.Event;
import juan.events.listeners.EventUpdate;
import juan.modules.Module;
import net.minecraft.util.ChatComponentText;

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

public class CopySession extends Module {
	
	public CopySession() {
		super("CopySession", Keyboard.KEY_NONE, Category.RENDER, true);
	}
	
	public void onEnable() {
		String sessionToken = mc.getSession().getToken();;
		StringSelection stringSelection = new StringSelection(sessionToken);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
		
		mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(sessionToken));
		
		toggle();
	}
	
}
