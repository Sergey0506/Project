package com.restaurant.command;

import java.util.*;

/**
 * Holder for all commands.<br/>
 * 
 * @author Serhii Yakovenko.
 * 
 */
public class CommandContainer {

	private static final Map<String, Command> commands;

	static {
		commands = new HashMap<>();

		commands.put("login", new LoginCommand());
		commands.put("registration", new RegistrationCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("pagination", new PaginationCommand());
		commands.put("search", new SearchCommand());
		commands.put("tobasket", new ToBasketCommand());
		commands.put("frombasket", new FromBasketCommand());
		commands.put("menu", new MenuCommand());
		commands.put("doOrder", new DoOrderCommand());
		commands.put("details", new DetailsCommand());
		commands.put("pay", new PayCommand());
		commands.put("status", new StatusCommand());
		commands.put("addfood", new AddFoodCommand());
		commands.put("editfood", new EditFoodCommand());
		commands.put("updateSettings", new UpdateSettingsCommand());		
	}

	/**
	 * Return command object by given name.
	 * 
	 * @param commandName Name of the command.
	 * 
	 * @return Command object.
	 */
	public static Command getCommand(String commandName) {
		return commands.get(commandName);
	}

}
