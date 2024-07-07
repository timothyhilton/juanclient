package juan.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import juan.command.impl.*;
import juan.events.listeners.EventChat;

public class CommandManager {

	public List<Command> commands = new ArrayList<Command>();
	public String prefix = ".";
	
	public CommandManager() {
		commands.add(new Toggle());
		commands.add(new Help());
	}
	
	public void handleChat(EventChat event) {
		String message = event.getMessage();
		
		if(!message.startsWith(prefix))
			return;
		
		message = message.substring(prefix.length());
		
		if(!(message.split(" ").length > 0))
			return;
		
		String commandName = message.split(" ")[0];
		
		boolean foundCommand = false;
		for(Command c : commands) {
			if(c.aliases.contains(commandName) || c.name.equalsIgnoreCase(commandName)) {
				c.onCommand(Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length), message);
				foundCommand = true;
				break;
			}
		}
		
		if(!foundCommand) {
			Command.addChatMessage("can't find command '" + commandName + "' - try .help");
		}
		
		event.setCancelled(true);
	}
}
