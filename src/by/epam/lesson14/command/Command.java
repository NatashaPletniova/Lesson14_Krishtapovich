package by.epam.lesson14.command;

import by.epam.lesson14.bean.Request;
import by.epam.lesson14.bean.Response;

public interface Command {

	Response execute(Request request);

}
