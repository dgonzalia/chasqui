package ar.edu.unq.chasqui.service.rest.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.chasqui.model.Caracteristica;
import ar.edu.unq.chasqui.model.Producto;

public class ProductoResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7556219779540939060L;

	private Integer idCategoria;
	private Integer idProducto;
	private Integer idFabricante;
	private String pathImagen;
	private String nombreProducto;
	private String nombreCategoria;
	private String nombreFabricante;
	private Double precio;
	private List<CaracteristicaResponse>caracteristicas;
	
	
	public Integer getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}
	public Integer getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}
	public Integer getIdFabricante() {
		return idFabricante;
	}
	public void setIdFabricante(Integer idFabricante) {
		this.idFabricante = idFabricante;
	}
	public String getPathImagen() {
		return pathImagen;
	}
	public void setPathImagen(String pathImagen) {
		this.pathImagen = pathImagen;
	}
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	public String getNombreCategoria() {
		return nombreCategoria;
	}
	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}
	public String getNombreFabricante() {
		return nombreFabricante;
	}
	public void setNombreFabricante(String nombreFabricante) {
		this.nombreFabricante = nombreFabricante;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public List<CaracteristicaResponse> getCaracteristicas() {
		return caracteristicas;
	}
	public void setCaracteristicas(List<CaracteristicaResponse> caracteristicas) {
		this.caracteristicas = caracteristicas;
	}
	
	
	
	public ProductoResponse (Producto p){
		this.idCategoria = p.getCategoria().getId();
		this.idFabricante = p.getFabricante().getId();
		this.idProducto =p.getId();
		this.pathImagen = p.getVariantes().get(0).getImagenes().get(0).getPath();
		this.precio = p.getVariantes().get(0).getPrecio();
		this.nombreProducto = p.getNombre();
		this.nombreFabricante = p.getFabricante().getNombre();
		this.nombreCategoria = p.getCategoria().getNombre();
		caracteristicas = new ArrayList<>();
		for(Caracteristica c : p.getCaracteristicas()){
			caracteristicas.add(new CaracteristicaResponse(c));
		}
	}
	
	
	
	
}
