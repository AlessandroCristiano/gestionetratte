package it.prova.gestionetratte.web.api.exception;

public class TratteAttiveNotFound extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public TratteAttiveNotFound(String message) {
		super(message);
	}

}
