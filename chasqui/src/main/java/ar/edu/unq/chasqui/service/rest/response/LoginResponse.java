package ar.edu.unq.chasqui.service.rest.response;

import ar.edu.unq.chasqui.model.Cliente;

public class LoginResponse {

	
	private String email;
	private String token;
	
	public LoginResponse(){
		
	}
	
	public LoginResponse(Cliente c){
		email = c.getEmail();
		token = c.getToken();
	}

	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	
	
	
}
