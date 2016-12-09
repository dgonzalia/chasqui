package ar.edu.unq.chasqui.model;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;

import ar.edu.unq.chasqui.view.composer.Constantes;

public class Pedido {
	
	private Integer id;
	private Integer idVendedor;
	private String estado;
	private String nombreVendedor;
	private String usuarioCreador;
	private Boolean alterable;
	private DateTime fechaCreacion;
	private DateTime fechaDeVencimiento;
	private Direccion direccionEntrega;
	private Double montoMinimo;
	private Double montoActual;
	private Boolean perteneceAPedidoGrupal;
	private Set<ProductoPedido>productosEnPedido;
	

	public Pedido(Vendedor v,Cliente c) {
		idVendedor = v.getId();
		nombreVendedor = v.getNombre();
		estado = Constantes.ESTADO_PEDIDO_ABIERTO;
		direccionEntrega = c.obtenerDireccionPredeterminada();
		usuarioCreador = c.getEmail();
		fechaCreacion = new DateTime();
		alterable = true;
		perteneceAPedidoGrupal = false;
		montoMinimo = new Double(v.getMontoMinimoPedido());
		montoActual = new Double(0.0);
		fechaDeVencimiento = v.getFechaCierrePedido();
		productosEnPedido = new HashSet<ProductoPedido>();
	}
	
	public Pedido(){}
	
	

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	public String getNombreVendedor() {
		return nombreVendedor;
	}

	public void setNombreVendedor(String nombreVendedor) {
		this.nombreVendedor = nombreVendedor;
	}

	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public DateTime getFechaCreacion() {
		return fechaCreacion;
	}
	
	public void setFechaCreacion(DateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public Direccion getDireccionEntrega() {
		return direccionEntrega;
	}
	
	public void setDireccionEntrega(Direccion direccionEntrega) {
		this.direccionEntrega = direccionEntrega;
	}
	
	
	public Boolean getPerteneceAPedidoGrupal() {
		return perteneceAPedidoGrupal;
	}

	public void setPerteneceAPedidoGrupal(Boolean perteneceAPedidoGrupal) {
		this.perteneceAPedidoGrupal = perteneceAPedidoGrupal;
	}

	public Double getMontoMinimo() {
		return montoMinimo;
	}

	public void setMontoMinimo(Double montoMinimo) {
		this.montoMinimo = montoMinimo;
	}

	public Double getMontoActual() {
		return montoActual;
	}

	public void setMontoActual(Double montoActual) {
		this.montoActual = montoActual;
	}	

	public Set<ProductoPedido> getProductosEnPedido() {
		return productosEnPedido;
	}

	public void setProductosEnPedido(Set<ProductoPedido> productosEnPedido) {
		this.productosEnPedido = productosEnPedido;
	}

	public Integer getIdVendedor() {
		return idVendedor;
	}
	
	public void setIdVendedor(Integer idVendedor) {
		this.idVendedor = idVendedor;
	}
	
	
	
	public Boolean getAlterable() {
		return alterable;
	}
	
	public void setAlterable(Boolean alterable) {
		this.alterable = alterable;
	}
	
	
	public DateTime getFechaDeVencimiento() {
		return fechaDeVencimiento;
	}
	
	public void setFechaDeVencimiento(DateTime fechaDeVencimiento) {
		this.fechaDeVencimiento = fechaDeVencimiento;
	}	
	
	
	//METHODS 

	public void confirmarte() {
		if(this.getEstado().equals(Constantes.ESTADO_PEDIDO_CONFIRMADO) && alterable ){
			this.estado = Constantes.ESTADO_PEDIDO_ENTREGADO;
			this.alterable=false;
		}
		
	}

	public boolean estaVigente() {
		return this.getEstado().equals(Constantes.ESTADO_PEDIDO_ABIERTO) &&
				this.getFechaDeVencimiento().isAfterNow();
	}

	public void agregarProductoPedido(ProductoPedido pp) {
		if(existeProductoEnPedido(pp)){
			sumarCantidadAProductoExistente(pp);
		}else{
			productosEnPedido.add(pp);			
		}
		
	}

	private void sumarCantidadAProductoExistente(ProductoPedido pp) {
		ProductoPedido p = encontrarProductoEnPedido(pp);
		p.sumarCantidad(pp.getCantidad());
	}

	private ProductoPedido encontrarProductoEnPedido(ProductoPedido pp) {
		for(ProductoPedido p : productosEnPedido){
			if(p.getIdVariante().equals(pp.getIdVariante())){
				return p;
			}
		}
		return null;
	}

	private boolean existeProductoEnPedido(ProductoPedido p) {
		return encontrarProductoEnPedido(p) != null;
	}

	public ProductoPedido encontrarProductoPedido(Integer idVariante) {
		for(ProductoPedido pp : this.getProductosEnPedido()){
			if(pp.getIdVariante().equals(idVariante)){
				return pp;
			}
		}
		return null;
	}

	public void eliminar(ProductoPedido pp) {
		productosEnPedido.remove(pp);		
	}
	
	public void sumarAlMontoActual (Double precio, Integer cantidad ) {
		this.montoActual += precio * cantidad;
	}
	
	public void restarAlMontoActual (Double precio, Integer cantidad ) {
		this.montoActual -= precio * cantidad;
	}
	


}
