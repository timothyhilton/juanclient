package juan.command.impl;

import juan.Client;
import juan.command.Command;
import juan.command.CommandManager;
import juan.modules.Module;

public class Help extends Command {

	public Help() {
		super("help", "shows a list of all commands", "help", "h");
	}
	
	public void onCommand(String[] args, String command) {
		for(Command c : Client.getCommandManager().commands) {
			Command.addChatMessage(c.name + " | " + c.description);
		}
	}

}
