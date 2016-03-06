package ar.edu.unq.chasqui.model;

import java.util.List;

import org.joda.time.DateTime;

public class Vendedor extends Usuario{

	private Integer id;
	private Integer montoMinimoPedido;
	private DateTime fechaCierrePedido;
	private String msjCierrePedido;
	private Integer distanciaCompraColectiva;
	private Imagen mapaZonas;
	private List<Categoria> categorias;
	private List<Fabricante> fabricantes;
	
	
	//GETs & SETs
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMontoMinimoPedido() {
		return montoMinimoPedido;
	}

	public void setMontoMinimoPedido(Integer montoMinimoPedido) {
		this.montoMinimoPedido = montoMinimoPedido;
	}

	public DateTime getFechaCierrePedido() {
		return fechaCierrePedido;
	}

	public void setFechaCierrePedido(DateTime fechaCierrePedido) {
		this.fechaCierrePedido = fechaCierrePedido;
	}

	public String getMsjCierrePedido() {
		return msjCierrePedido;
	}

	public void setMsjCierrePedido(String msjCierrePedido) {
		this.msjCierrePedido = msjCierrePedido;
	}

	public Integer getDistanciaCompraColectiva() {
		return distanciaCompraColectiva;
	}

	public void setDistanciaCompraColectiva(Integer distanciaCompraColectiva) {
		this.distanciaCompraColectiva = distanciaCompraColectiva;
	}

	public Imagen getMapaZonas() {
		return mapaZonas;
	}

	public void setMapaZonas(Imagen mapaZonas) {
		this.mapaZonas = mapaZonas;
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

	//METHODS
	
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
	
	public void eliminarProductor (Fabricante f) {
		fabricantes.remove(f);
	}
	
	
	public void agregarCategoria (Categoria categoria){
		categorias.add(categoria);
	}
	
	public void notificarCierrePedido () {
		//TODO
	}
	
	public void notificarFechaEntrega() {
		//TODO
	}
}
