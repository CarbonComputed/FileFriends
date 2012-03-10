package ServerConnection;

public class InvalidInputException extends Exception {
	
	private String message;
	public InvalidInputException(String message){
		super();
		this.message=message;
	}
	public String getMessage(){
		return "InvalidInput: "+message;
	}
	

}
