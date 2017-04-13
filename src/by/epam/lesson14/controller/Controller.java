package by.epam.lesson14.controller;

import by.epam.lesson14.bean.Request;
import by.epam.lesson14.bean.Response;
import by.epam.lesson14.command.Command;
import by.epam.lesson14.command.CommandName;
import by.epam.lesson14.command.impl.DestroyConnectionPoolCommand;
import by.epam.lesson14.command.impl.InitConnectionPoolCommand;

public final class Controller {
	private final CommandProvider provider = new CommandProvider();

	public Controller() {
		InitConnectionPoolCommand command = new InitConnectionPoolCommand();
		command.execute();
	}

	public Response doAction(Request request) {

		CommandName commandName = request.getCommandName();
		Command command = provider.getCommand(commandName);
		Response response = command.execute(request);

		return response;
	}

	public void destroyConnectionResources() {
		DestroyConnectionPoolCommand command = new DestroyConnectionPoolCommand();
		command.execute();
	}

}
