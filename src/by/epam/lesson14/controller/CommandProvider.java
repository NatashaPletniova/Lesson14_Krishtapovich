package by.epam.lesson14.controller;

import java.util.HashMap;
import java.util.Map;
import by.epam.lesson14.command.Command;
import by.epam.lesson14.command.CommandName;
import by.epam.lesson14.command.impl.AddProductItemCommand;
import by.epam.lesson14.command.impl.DeleteProductItemByIDCommand;
import by.epam.lesson14.command.impl.RentProductItemCommand;
import by.epam.lesson14.command.impl.ShowProductByNameCommand;
import by.epam.lesson14.command.impl.ShowProductItemAvailabilityCommand;

public class CommandProvider {

	private final Map<CommandName, Command> provider = new HashMap<>();

	CommandProvider() {
		provider.put(CommandName.SHOW_PRODUCT_BY_NAME, new ShowProductByNameCommand());
		provider.put(CommandName.SHOW_PRODUCT_ITEM_AVAILABILITY, new ShowProductItemAvailabilityCommand());
		provider.put(CommandName.DELETE_PRODUCTITEM_BY_ID, new DeleteProductItemByIDCommand());
		provider.put(CommandName.RENT_PRODUCTITEM, new RentProductItemCommand());
		provider.put(CommandName.ADD_PRODUCTITEM, new AddProductItemCommand());

	}

	public Command getCommand(CommandName commandName) {
		Command command;
		command = provider.get(commandName);
		return command;
	}

}
