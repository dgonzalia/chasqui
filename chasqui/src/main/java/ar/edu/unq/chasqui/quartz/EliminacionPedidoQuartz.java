package ar.edu.unq.chasqui.quartz;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.chasqui.model.Pedido;
import ar.edu.unq.chasqui.services.interfaces.PedidoService;
import ar.edu.unq.chasqui.services.interfaces.UsuarioService;

public class EliminacionPedidoQuartz {

	@Autowired PedidoService pedidoService;
	@Autowired String nombreServidor;
	@Autowired UsuarioService usuarioService;
	
	
	public void execute(){
		if(obtenerHostname().equals(nombreServidor)){
			List<Pedido> ps = pedidoService.obtenerPedidosVencidos();
			for(Pedido p : ps){
				usuarioService.eliminarPedidoPara(p.getUsuarioCreador(), p.getId());
			}
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
