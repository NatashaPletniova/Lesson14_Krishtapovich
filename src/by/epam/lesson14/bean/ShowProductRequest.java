package by.epam.lesson14.bean;

public class ShowProductRequest extends Request {
	private String name;

	public ShowProductRequest() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
