package by.epam.lesson14.bean;

import by.epam.lesson14.command.CommandName;

public class Request {
	private CommandName commandName;

	public CommandName getCommandName() {
		return commandName;
	}

	public void setCommandName(CommandName commandName) {
		this.commandName = commandName;
	}

}
