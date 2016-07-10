package ar.edu.unq.chasqui.service.rest.request;

import java.io.Serializable;

public class SingUpRequest implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1485267484736523922L;
	private String email;
	private String password;
	private String nickName;
	private String nombre;
	private String apellido;
	private String telefonoFijo;
	private String telefonoMovil;
	//private DireccionRequest direccion;
	
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getTelefonoFijo() {
		return telefonoFijo;
	}
	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}
	public String getTelefonoMovil() {
		return telefonoMovil;
	}
	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}
//	public DireccionRequest getDireccion() {
//		return direccion;
//	}
//	public void setDireccion(DireccionRequest direccion) {
//		this.direccion = direccion;
//	}
	
	
	
	
	
	
	
	
}
