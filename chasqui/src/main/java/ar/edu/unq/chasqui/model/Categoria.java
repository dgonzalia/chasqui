package ar.edu.unq.chasqui.model;

public class Categoria {

	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String descripcion) {
		this.nombre = descripcion;
	}
	
	@Override
	public String toString(){
		return this.getNombre();
	}
	
	
	
}
