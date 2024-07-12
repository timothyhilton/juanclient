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
				Command.addChatMessage("Themes: -----------------");
				for(String theme : Client.theme.getThemes()) {
					Command.addChatMessage(theme);
				}
				Command.addChatMessage("-----------------------");
				break;
			case "set":
				boolean foundTheme = false;
				String themeInputted = args[1];
				if(args.length >= 3)
					themeInputted += " " + args[2];

				for(String theme : Client.theme.getThemes()) {
					if(theme.equalsIgnoreCase(themeInputted)) {
						foundTheme = true;
						Command.addChatMessage("set theme to " + theme);
						Client.theme.getThemeSetting().setMode(theme);
					}
					System.out.println(theme);
					
				}
				if(!foundTheme)
					Command.addChatMessage("couldn't find " + themeInputted);
				break;
			default:
				Command.addChatMessage("usage: .theme <list|set> <theme?>");
		}
	}

}
