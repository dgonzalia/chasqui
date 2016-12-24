package ar.edu.unq.chasqui.quartz;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.cxf.common.util.StringUtils;
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
		if(obtenerHostname().equals(nombreServidor)){
			List<Vendedor>vendedores = vendedorService.obtenerVendedores();
			for(Vendedor v : vendedores){
				enviarNotificacionesDePedidos(v);
			}					
		}
		
	}
	
	@Transactional
	public void enviarNotificacionesDePedidos(Vendedor v) {
		try{
			List<Pedido>pedidos = pedidoService.obtenerPedidosProximosAVencer(cantidadDeDiasParaEnviarNotificacion, v.getId(), v.getFechaCierrePedido());
			for(Pedido p : pedidos){
				DateTime dt = new DateTime();
				DateTime fechaCierre = v.getFechaCierrePedido();
				Cliente c = (Cliente) usuarioService.obtenerUsuarioPorEmail(p.getUsuarioCreador());
				if(dt.getDayOfYear() == fechaCierre.getDayOfYear()){
					mailService.enviarEmailNotificacionPedido(p.getUsuarioCreador(), cuerpoEmail, c.getNombre(), c.getApellido());
				}else{
					String mensajeNotificacion = obtenerMensajeNotificacion(v);
					Notificacion n = new Notificacion("Chasqui", p.getUsuarioCreador(), mensajeNotificacion, Constantes.ESTADO_NOTIFICACION_NO_LEIDA);
					notificacionService.guardar(n,c.getIdDispositivo());
				}
			}			
		}catch(Exception e){
			e.printStackTrace();
			//logear
		}
	}


	private String obtenerMensajeNotificacion(Vendedor v) {
		if(!StringUtils.isEmpty(v.getMsjCierrePedido())){
			return mensajeNotificacionChasqui;
		}
		return v.getMsjCierrePedido();
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
