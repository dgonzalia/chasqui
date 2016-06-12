package ar.edu.unq.chasqui.service.rest.response;

import ar.edu.unq.chasqui.model.Cliente;

public class PerfilResponse {

	
	  private String email;
	  private String nickName;
	  private String nombre;
	  private String apellido;
	  private Integer telefonoFijo;
	  private Integer telefonoMovil;
	  private DireccionResponse direccion;
	  
	public PerfilResponse(Cliente c){
		this.email = c.getEmail();
		this.nickName = c.getNickName();
		this.nombre = c.getNombre();
		this.apellido = c.getApellido();
		this.telefonoFijo = c.getTelefonoFijo();
		this.telefonoMovil = c.getTelefonoMovil();
		this.direccion = new DireccionResponse(c.getDireccionPredeterminada());
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Integer getTelefonoFijo() {
		return telefonoFijo;
	}

	public void setTelefonoFijo(Integer telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	public Integer getTelefonoMovil() {
		return telefonoMovil;
	}

	public void setTelefonoMovil(Integer telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	public DireccionResponse getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionResponse direccion) {
		this.direccion = direccion;
	}
	
	
	
	
	
	
	
	
}
