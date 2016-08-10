package ar.edu.unq.chasqui.service.rest.response;

import ar.edu.unq.chasqui.model.Cliente;

public class LoginResponse {

	
	private String email;
	private String token;
	private Integer id;
	private String nickname;
	
	public LoginResponse(){
		
	}
	
	public LoginResponse(Cliente c){
		email = c.getEmail();
		token = c.getToken();
		id = c.getId();
		nickname = c.getNickName();
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
	
	
	
}
