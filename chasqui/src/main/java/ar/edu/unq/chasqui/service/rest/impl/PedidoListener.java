package ar.edu.unq.chasqui.service.rest.impl;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ar.edu.unq.chasqui.exceptions.PedidoInexistenteException;
import ar.edu.unq.chasqui.exceptions.PedidoVigenteException;
import ar.edu.unq.chasqui.exceptions.UsuarioExistenteException;
import ar.edu.unq.chasqui.model.Pedido;
import ar.edu.unq.chasqui.service.rest.response.PedidoResponse;
import ar.edu.unq.chasqui.services.interfaces.ProductoService;
import ar.edu.unq.chasqui.services.interfaces.UsuarioService;

@Service
@Path("/pedido")
public class PedidoListener {
	
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	ProductoService productoService;
	
	
	
	@POST
	@Produces("application/json")
	@Path("/individual")
	public Response crearPedidoIndividualParaUsuario(@HeaderParam("idVendedor") Integer idVendedor){
		String mail = obtenerEmailDeContextoDeSeguridad();
		try{
			usuarioService.crearPedidoPara(mail,idVendedor);
			return Response.ok().build();
		}catch(PedidoVigenteException | UsuarioExistenteException e){
			return Response.status(406).entity(e.getMessage()).build();
		}catch(Exception e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Produces("application/json")
	@Path("/individual/{idVendedor}")
	public Response obtenerPedidoActual(@PathParam("idVendedor")Integer idVendedor){
		String mail =  obtenerEmailDeContextoDeSeguridad();
		try{
			return Response.ok(toResponse(usuarioService.obtenerPedidoActualDe(mail,idVendedor))).build();
		}catch(UsuarioExistenteException | PedidoInexistenteException e){
			return Response.status(404).entity(e.getMessage()).build();
		}
		catch(Exception e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}	
	
	
	private PedidoResponse toResponse(Pedido pedido) {
		return new PedidoResponse(pedido);
	}
	private String obtenerEmailDeContextoDeSeguridad(){
		return 	SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	

}
