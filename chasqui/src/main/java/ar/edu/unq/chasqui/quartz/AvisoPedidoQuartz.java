package ar.edu.unq.chasqui.quartz;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.chasqui.model.Cliente;
import ar.edu.unq.chasqui.model.Notificacion;
import ar.edu.unq.chasqui.model.Pedido;
import ar.edu.unq.chasqui.model.Vendedor;
import ar.edu.unq.chasqui.services.impl.MailService;
import ar.edu.unq.chasqui.services.interfaces.NotificacionService;
import ar.edu.unq.chasqui.services.interfaces.PedidoService;
import ar.edu.unq.chasqui.services.interfaces.UsuarioService;
import ar.edu.unq.chasqui.services.interfaces.VendedorService;
import ar.edu.unq.chasqui.view.composer.Constantes;

public class AvisoPedidoQuartz {


	@Autowired
	UsuarioService usuarioService;
	@Autowired
	VendedorService vendedorService;
	@Autowired
	PedidoService pedidoService;
	@Autowired
	String nombreServidor;
	@Autowired
	Integer cantidadDeDiasParaEnviarNotificacion;
	@Autowired
	String cuerpoEmail;
	@Autowired
	String mensajeNotificacionChasqui;
	@Autowired
	NotificacionService notificacionService;
	@Autowired
	MailService mailService;
	
	
	public void execute(){
		//AGREGAR VALIDACION DE SERVIDOR
//		if(obtenerHostname().equals("hola")){
			List<Vendedor>vendedores = vendedorService.obtenerVendedores();
			for(Vendedor v : vendedores){
				if(v.getUsername().equals("MatLock")){
					enviarNotificacionesDePedidos(v);					
				}
			}					
//		}
		
	}
	
	@Transactional
	public void enviarNotificacionesDePedidos(Vendedor v) {
		try{
			List<Pedido>pedidos = pedidoService.obtenerPedidosProximosAVencer(cantidadDeDiasParaEnviarNotificacion, v.getId(), v.getFechaCierrePedido());
			for(Pedido p : pedidos){
				DateTime dt = new DateTime();
				DateTime fechaCierre = v.getFechaCierrePedido();//.plusDays(-1);
				if(dt.getDayOfYear() == fechaCierre.getDayOfYear()){
					Cliente c = (Cliente) usuarioService.obtenerUsuarioPorEmail(p.getUsuarioCreador());
					mailService.enviarEmailNotificacionPedido(p.getUsuarioCreador(), cuerpoEmail, c.getNombre(), c.getApellido());
				}else{
					Notificacion n = new Notificacion("Chasqui", p.getUsuarioCreador(), mensajeNotificacionChasqui, Constantes.ESTADO_NOTIFICACION_NO_LEIDA);
					notificacionService.guardar(n);
				}
			}			
		}catch(Exception e){
			e.printStackTrace();
			//logear
		}
	}


	private String obtenerHostname(){
		InetAddress inetAddr;
		try {
			inetAddr = InetAddress.getLocalHost();
			return  inetAddr.getHostName();  
		} catch (UnknownHostException e) {
			return nombreServidor;
		}
	}
}
