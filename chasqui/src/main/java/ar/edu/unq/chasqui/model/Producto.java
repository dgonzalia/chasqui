package ar.edu.unq.chasqui.model;

import java.util.ArrayList;
import java.util.List;

public class Producto {

	
	private int id;
	private String nombre;
	private Categoria categoria;
	private String descripcion;
	private List<Caracteristica> caracteristicas;
	private Fabricante fabricante;
	private List<Variante>variantes;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public List<Caracteristica> getCaracteristicas() {
		return caracteristicas;
	}
	public void setCaracteristicas(List<Caracteristica> caracteristicas) {
		this.caracteristicas = caracteristicas;
	}
	public Fabricante getFabricante() {
		return fabricante;
	}
	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	public List<Variante> getVariantes() {
		return variantes;
	}
	public void setVariantes(List<Variante> variantes) {
		this.variantes = variantes;
	}
	public static Producto crearProductoEmpty(){
		Producto p = new Producto();
		p.setCaracteristicas(new ArrayList<Caracteristica>());
		p.setFabricante(new Fabricante());
		p.setVariantes(new ArrayList<Variante>());
		return p;
	}
	
}
