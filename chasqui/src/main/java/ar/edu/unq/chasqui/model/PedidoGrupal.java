package ar.edu.unq.chasqui.model;

import java.util.List;
import java.util.Map;

public class PedidoGrupal extends Pedido{

	private Integer id;
	private Map<Cliente, List<Producto>> usuariosParticipantes;
	private String descripcion;
	
	//GETs & SETs
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Map<Cliente, List<Producto>> getUsuariosParticipantes() {
		return usuariosParticipantes;
	}
	
	public void setUsuariosParticipantes(Map<Cliente, List<Producto>> usuariosParticipantes) {
		this.usuariosParticipantes = usuariosParticipantes;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}	
	
	
	//METHODS
	
	public void agregarMiembro () {
		//TODO
	}
	
	
}
