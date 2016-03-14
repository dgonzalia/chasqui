package ar.edu.unq.chasqui.model;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

public class Pedido {
	
	private Integer id;
	private Integer idVendedor;
	private String estado;
	private Cliente usuarioCreador;
	private DateTime fechaCreacion;
	private Direccion direccionEntrega;
	private Double montoMinimo;
	private Double montoActual;
	private List<UsuarioParticipante> usuariosParticipantes;
	
	//GETs & SETs
	
	public Pedido(int i, String string, Date date, Double j, Double k, String estadoPedidoAbierto) {
		id=i;
		Cliente c = new Cliente();
		c.setEmail(string);
		usuarioCreador = c;
		fechaCreacion = new DateTime(date.getTime());
		montoMinimo = j;
		montoActual = k;
		estado = estadoPedidoAbierto;
	}

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
	
	public Cliente getUsuarioCreador() {
		return usuarioCreador;
	}
	
	public void setUsuarioCreador(Cliente usuarioCreador) {
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

	public List<UsuarioParticipante> getUsuariosParticipantes() {
		return usuariosParticipantes;
	}
	
	public void setUsuariosParticipantes(List<UsuarioParticipante> usuariosParticipantes) {
		this.usuariosParticipantes = usuariosParticipantes;
	}
	

	public Integer getIdVendedor() {
		return idVendedor;
	}
	
	public void setIdVendedor(Integer idVendedor) {
		this.idVendedor = idVendedor;
	}
	
	
	
	//METHODS 


	public void agregarProducto(Producto p){
		//TODO
	}


	public void editarPedido () {
		//TODO
	}


}
