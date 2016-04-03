package ar.edu.unq.chasqui.security;

public class PasswordGenerator {

	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijkmnopqrstuvwxyz";
	  
	  
	public static String generateRandomToken() {
		  StringBuilder builder = new StringBuilder();
		  int lenght = 10;
	  		while (lenght-- != 0) {
	  			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
	  			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
	  		}
	  		return builder.toString();
	  }
}
