package ar.edu.unq.chasqui.model;

import java.util.Date;
import java.util.List;

public class Usuario {

	
	private String username;
	private String pathImagen;
	private Integer kilometroSeleccionado;
	private String password;
	private Date fechaProximaEntrega;
	private Integer montoMinimoCompra;
	private String email;
	private List<Categoria> categorias;
	private List<Fabricante>fabricantes;
	private List<Producto>productos;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	

	public String getPathImagen() {
		return pathImagen;
	}

	public void setPathImagen(String pathImagen) {
		this.pathImagen = pathImagen;
	}

	public Integer getKilometroSeleccionado() {
		return kilometroSeleccionado;
	}

	public void setKilometroSeleccionado(Integer kilometroSeleccionado) {
		this.kilometroSeleccionado = kilometroSeleccionado;
	}

	public Integer getMontoMinimoCompra() {
		return montoMinimoCompra;
	}

	public void setMontoMinimoCompra(Integer montoMinimoCompra) {
		this.montoMinimoCompra = montoMinimoCompra;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaProximaEntrega() {
		return fechaProximaEntrega;
	}

	public void setFechaProximaEntrega(Date fechaProximaEntrega) {
		this.fechaProximaEntrega = fechaProximaEntrega;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	
	
	
	
	
	public boolean contieneProductor(String nombreProductor){
		for(Fabricante f : fabricantes ){
			if( f.getNombre().equalsIgnoreCase(nombreProductor)){
				return true;
			}
		}
		return false;
	}

	public void agregarProductor(Fabricante f) {
		fabricantes.add(f);
	}
	
	public void agregarCategoria(Categoria c){
		categorias.add(c);
	}
	
	public void agregarProducto(Producto p){
		productos.add(p);
	}
	
	
	
	
}
