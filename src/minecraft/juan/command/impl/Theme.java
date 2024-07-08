package juan.command.impl;

import juan.Client;
import juan.command.Command;
import juan.modules.Module;

public class Theme extends Command {

	public Theme() {
		super("theme", "helps you manage themes", "theme <list|set>", "th");
	}
	
	public void onCommand(String[] args, String command) {
		if(args.length <= 0)
			return;
		
		switch(args[0]) {
			case "list":
				Command.addChatMessage("Themes:");
				for(String theme : Client.theme.getThemes()) {
					Command.addChatMessage(theme);
				}
				break;
			case "set":
				break;
			default:
				Command.addChatMessage("usage: .theme <list|set> <theme?>");
		}
	}

}
