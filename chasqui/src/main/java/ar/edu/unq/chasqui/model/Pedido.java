package ar.edu.unq.chasqui.model;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;

import ar.edu.unq.chasqui.view.composer.Constantes;

public class Pedido {
	
	private Integer id;
	private Integer idVendedor;
	private String estado;
	private String usuarioCreador;
	private Boolean alterable;
	private DateTime fechaCreacion;
	private DateTime fechaDeVencimiento;
	private Direccion direccionEntrega;
	private Double montoMinimo;
	private Double montoActual;
	private Boolean perteneceAPedidoGrupal;
	private Set<ProductoPedido>productosEnPedido;
	

	public Pedido(Vendedor v,String email) {
		idVendedor = v.getId();
		estado = Constantes.ESTADO_PEDIDO_ABIERTO;
		usuarioCreador = email;
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
		}
		this.alterable=false;
		
	}

	public boolean estaVigente() {
		return this.getEstado().equals(Constantes.ESTADO_PEDIDO_ABIERTO) &&  !this.getFechaDeVencimiento().isBeforeNow();
	}

	public void agregarProductoPedido(ProductoPedido pp) {
		productosEnPedido.add(pp);
		
	}
	
	
	


}
