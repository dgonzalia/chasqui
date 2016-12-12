package ar.edu.unq.chasqui.service.rest.response;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.chasqui.model.Pedido;
import ar.edu.unq.chasqui.model.ProductoPedido;

public class PedidoResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3034619190993190512L;
	
	
	private Integer id;
	private Integer idVendedor;
	private String estado;
	private String fechaCreacion;
	private String fechaVencimiento;
	private Double montoMinimo;
	private Double montoActual;
	private String nombreVendedor;
	private List<ProductoPedidoResponse> productosResponse;	
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getEstado() {
		return estado;
	}

	public Integer getIdVendedor() {
		return idVendedor;
	}


	public void setIdVendedor(Integer idVendedor) {
		this.idVendedor = idVendedor;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getFechaCreacion() {
		return fechaCreacion;
	}


	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}


	public String getFechaVencimiento() {
		return fechaVencimiento;
	}


	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
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
	
	
	



	public String getNombreVendedor() {
		return nombreVendedor;
	}


	public void setNombreVendedor(String nombreVendedor) {
		this.nombreVendedor = nombreVendedor;
	}


	public List<ProductoPedidoResponse> getProductosResponse() {
		return productosResponse;
	}


	public void setProductosResponse(List<ProductoPedidoResponse> productosResponse) {
		this.productosResponse = productosResponse;
	}


	public PedidoResponse(){}
	
	
	public PedidoResponse(Pedido p){
		id = p.getId();
		idVendedor = p.getIdVendedor();
		estado = p.getEstado();
		nombreVendedor=p.getNombreVendedor();
		DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		fechaCreacion = f.format(p.getFechaCreacion().toDate());
		fechaVencimiento = f.format(p.getFechaDeVencimiento().toDate());
		montoMinimo = p.getMontoMinimo();
		montoActual = p.getMontoActual();
		productosResponse = new ArrayList<ProductoPedidoResponse>();
		if (p.getProductosEnPedido() != null) {
			for(ProductoPedido pp : p.getProductosEnPedido()){
				productosResponse.add(new ProductoPedidoResponse(pp));
			}
		}
	}
	

}
