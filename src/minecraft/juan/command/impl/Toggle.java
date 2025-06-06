package juan.command.impl;

import juan.Client;
import juan.command.Command;
import juan.modules.Module;

public class Toggle extends Command {

	public Toggle() {
		super("toggle", "toggles a module by name", "toggle <module>", "t");
	}
	
	public void onCommand(String[] args, String command) {
		if(args.length <= 0)
			return;
		
		String moduleName = args[0];
		
		boolean foundModule = false;
		
		for(Module module : Client.modules) {
			if(!module.name.equalsIgnoreCase(moduleName))
				continue;
			
			module.toggle();
			
			Command.addChatMessage((module.isEnabled() ? "enabled " : "disabled ") + module.name);
			foundModule = true;
			break;
		}
		
		if(!foundModule)
			Command.addChatMessage("couldn't find module '" + moduleName + "'");
	}

}
