package ar.edu.unq.chasqui.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.cxf.common.util.StringUtils;

import ar.edu.unq.chasqui.exceptions.DireccionesInexistentes;
import ar.edu.unq.chasqui.exceptions.PedidoInexistenteException;
import ar.edu.unq.chasqui.security.PasswordGenerator;
import ar.edu.unq.chasqui.service.rest.request.DireccionRequest;
import ar.edu.unq.chasqui.service.rest.request.EditarPerfilRequest;
import ar.edu.unq.chasqui.service.rest.request.SingUpRequest;
import ar.edu.unq.chasqui.view.composer.Constantes;

public class Cliente extends Usuario{

	
	private String nombre;
	private String apellido;
	private String nickName;
	private String telefonoFijo;
	private String telefonoMovil;
	private List<Direccion> direccionesAlternativas;
	private List<Notificacion> notificaciones;
	private Historial historialPedidos;
	private List<Pedido> pedidos;
	
	// Atributos necesarios para la integracion con spring security
	private String rol = "ROLE_USER";
	private String token;
 
	//GETs & SETs
	
	
	public Cliente(){
		
	}
	
	public Cliente(SingUpRequest request,String nuevoToken) throws Exception {
		nombre = request.getNombre();
		apellido = request.getApellido();
		nickName= request.getNickName();
		email = request.getEmail();
		telefonoFijo = request.getTelefonoFijo();
		telefonoMovil = request.getTelefonoMovil();
		direccionesAlternativas = new ArrayList<Direccion>();
		token = nuevoToken;
		password = request.getPassword();
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public String getTelefonoFijo() {
		return telefonoFijo;
	}
	
	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}
	
	public String getTelefonoMovil() {
		return telefonoMovil;
	}
	
	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	
	public List<Direccion> getDireccionesAlternativas() {
		return direccionesAlternativas;
	}
	
	public void setDireccionesAlternativas(List<Direccion> direccionesAlternativas) {
		this.direccionesAlternativas = direccionesAlternativas;
	}
	
	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}
	
	public void setNotificaciones(List<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}
	
	public Historial getHistorialPedidos() {
		return historialPedidos;
	}
	
	public void setHistorialPedidos(Historial historialPedidos) {
		this.historialPedidos = historialPedidos;
	}	

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void modificarCon(EditarPerfilRequest editRequest) throws Exception {
		if(!StringUtils.isEmpty(editRequest.getApellido())){
			this.apellido = editRequest.getApellido();
		}
		
		if(!StringUtils.isEmpty(editRequest.getNickName())){
			this.nickName = editRequest.getNickName();
		}
		if(!StringUtils.isEmpty(editRequest.getNombre())){
			this.nombre = editRequest.getNombre();
		}
		if(!StringUtils.isEmpty(editRequest.getPassword())){
			this.password = editRequest.getPassword();
		}
		if(editRequest.getTelefonoFijo() != null){
			this.telefonoFijo = editRequest.getTelefonoFijo();
		}
		if(editRequest.getTelefonoMovil() != null){
			this.telefonoMovil = editRequest.getTelefonoMovil();
		}
//		Direccion d = this.obtenerDireccionPredeterminada();
//		d.modificarCon(editRequest.getDireccion());
		
		
	}

	public Direccion obtenerDireccionPredeterminada() {
		for(Direccion d : this.getDireccionesAlternativas()){
			if(d.getPredeterminada()){
				return d;
			}
		}
		return null;
	}

	public void agregarDireccion(DireccionRequest request) {
		Direccion d = new Direccion(request);
		Direccion predeterminada = this.obtenerDireccionPredeterminada();
		if(request.getPredeterminada()){
			predeterminada.setPredeterminada(false);
		}
		this.direccionesAlternativas.add(d);
		
	}

	public void editarDireccionCon(DireccionRequest request,Integer idDireccion) throws DireccionesInexistentes {
		Integer altura = request.getAltura();
		String calle= request.getCalle();
		String alias =request.getAlias();
		String localidad = request.getLocalidad();
		String departamento = request.getDepartamento();
		String codigoPostal = request.getCodigoPostal();
		String latitud = request.getLatitud();
		String longitud = request.getLongitud();
		boolean predeterminada = request.getPredeterminada();
		
		if(predeterminada){
			Direccion pre = this.obtenerDireccionPredeterminada();
			if (pre != null) {
				pre.setPredeterminada(false);
			}
		}
		
		
		if (contieneDireccionConId(idDireccion, direccionesAlternativas)) {
			for(Direccion d : direccionesAlternativas){
				if(idDireccion.equals(d.getId())){
					d.editate(altura,calle,alias,localidad,departamento,codigoPostal,latitud,longitud,predeterminada);
				}
			}
		}
		else {
			throw new DireccionesInexistentes(); 
		}
	}

	private boolean contieneDireccionConId(Integer idDireccion, List<Direccion> direccionesAlternativas) {
		for (Direccion d : direccionesAlternativas) {
			if(idDireccion.equals(d.getId())){
				return true;
			}
		}
		return false;
	}

	public void eliminarDireccion(Integer idDireccion) throws DireccionesInexistentes {
		if (contieneDireccionConId(idDireccion, direccionesAlternativas)) {
			Iterator<Direccion>it = direccionesAlternativas.iterator();
			while(it.hasNext()){
				Direccion d = it.next();
				if(d.getId().equals(idDireccion)){
					it.remove();
				}
			}
		}
		else {
			throw new DireccionesInexistentes(); 
		}
		
	}

	public Pedido obtenerPedidoActualDe(Integer idVendedor) throws PedidoInexistenteException {
		for(Pedido p : pedidos){
			if(p.getIdVendedor().equals(idVendedor) && p.getEstado().equals(Constantes.ESTADO_PEDIDO_ABIERTO)){
				return p;
			}
		}		
		throw new PedidoInexistenteException("El usuario: "+this.getNickName()+" no posee ningun pedido vigente ");
	}

	public boolean contienePedidoVigenteParaVendedor(Integer idVendedor) {
		
		for(Pedido p : pedidos){
			if(p.getIdVendedor().equals(idVendedor) && p.estaVigente()){
				return true;
			}
		}
		
		return false;
	}

	public void agregarPedido(Pedido p) {
		this.pedidos.add(p);
		
	}
	
	
	
	
	
	
		
}
