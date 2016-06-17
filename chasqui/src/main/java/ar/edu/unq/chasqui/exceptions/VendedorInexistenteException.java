package ar.edu.unq.chasqui.exceptions;

public class VendedorInexistenteException extends RuntimeException {		

		public VendedorInexistenteException(Exception e){
			super(e);
		}
		
		public VendedorInexistenteException(Exception e , String mensaje){
			super(mensaje,e);
		}
		
		
		public VendedorInexistenteException(String msg){
			super(msg);
		}

		public VendedorInexistenteException() {
			// TODO Auto-generated constructor stub
		}
		
	}
