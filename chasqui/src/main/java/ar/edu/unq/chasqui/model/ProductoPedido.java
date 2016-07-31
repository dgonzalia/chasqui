package ar.edu.unq.chasqui.model;

public class ProductoPedido {

	private Integer id;
	private Integer idVariedad;
	private Double precio;
	private String nombreProducto;
	private String nombreVariante;
	private Integer cantidad;
	private String imagen;
	
	public ProductoPedido(Variante v,Integer cant) {
		idVariedad = v.getId();
		cantidad = cant;
		nombreProducto = v.getProducto().getNombre();
		nombreVariante = v.getNombre();
		precio = v.getPrecio();
		imagen = v.getImagenes().get(0).getPath();
	}

	//GETs & SETs
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Integer getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	public String getNombreVariante() {
		return nombreVariante;
	}
	public void setNombreVariante(String nombreVariante) {
		this.nombreVariante = nombreVariante;
	}

	public Integer getIdVariedad() {
		return idVariedad;
	}

	public void setIdVariedad(Integer idVariedad) {
		this.idVariedad = idVariedad;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public void restar(Integer cant) {
		cantidad -= cant;
	}
	
	
	
	
}
