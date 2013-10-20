package clueGame;

public class BadConfigFormatException extends Exception{
	String message = "";
	public BadConfigFormatException() {}
	
	public BadConfigFormatException(String message)
	{
		super(message);
		this.message = message;
	}
	
	
	public String toString() {
		return "Bad format in configuration files (map or legend):\n" + message;
	}

}
