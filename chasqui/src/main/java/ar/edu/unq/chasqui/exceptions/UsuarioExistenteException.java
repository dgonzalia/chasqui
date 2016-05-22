package ar.edu.unq.chasqui.exceptions;

public class UsuarioExistenteException extends RuntimeException {

	
	

	public UsuarioExistenteException(Exception e){
		super(e);
	}
	
	public UsuarioExistenteException(Exception e , String mensaje){
		super(mensaje,e);
	}
	
	
	public UsuarioExistenteException(String msg){
		super(msg);
	}

	public UsuarioExistenteException() {
		// TODO Auto-generated constructor stub
	}
	
}
