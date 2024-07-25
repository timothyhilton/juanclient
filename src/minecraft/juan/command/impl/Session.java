package juan.command.impl;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import juan.Client;
import juan.command.Command;
import juan.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class Session extends Command {

	public Session() {
		super("session", "tools to manage session stuff", "session <copy|print>", "s");
	}
	
	public void onCommand(String[] args, String command) {
		Minecraft mc = Minecraft.getMinecraft();
		
		if(args.length <= 0)
			return;
		
		String sessionToken = mc.getSession().getToken();;

		switch(args[0]) {
				
			case "copy":
				StringSelection stringSelection = new StringSelection(sessionToken);
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(stringSelection, null);
				Command.addChatMessage("copied session successfully");
				return;
				
			case "print":
				Command.addChatMessage("session is: " + sessionToken);
				return;
			
			default:
				Command.addChatMessage("usage: " + this.syntax);
		}
	}
}
