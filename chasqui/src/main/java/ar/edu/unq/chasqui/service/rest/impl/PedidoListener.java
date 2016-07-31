package ar.edu.unq.chasqui.service.rest.impl;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ar.edu.unq.chasqui.exceptions.PedidoInexistenteException;
import ar.edu.unq.chasqui.exceptions.PedidoVigenteException;
import ar.edu.unq.chasqui.exceptions.RequestIncorrectoException;
import ar.edu.unq.chasqui.exceptions.UsuarioExistenteException;
import ar.edu.unq.chasqui.model.Pedido;
import ar.edu.unq.chasqui.service.rest.request.AgregarProductoAPedidoRequest;
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
			return Response.status(201).build();
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
		}catch(PedidoInexistenteException e){
			return Response.status(404).entity(e.getMessage()).build();
		}
		catch(Exception e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}	
	
	
	@PUT
	@Produces("application/json")
	@Path("/individual")
	public Response agregarProductoAPedido(@Multipart(value="agregarRequest", type="application/json")final String agregarRequest){
		try{
			AgregarProductoAPedidoRequest request = toAgregarPedidoRequest(agregarRequest);
			String email = obtenerEmailDeContextoDeSeguridad();
			validarRequest(request);
			usuarioService.agregarPedidoA(request,email);
			return Response.ok().build();
		}catch(IOException | RequestIncorrectoException e ){
			return Response.status(406).entity("Parametros Incorrectos").build();
		}catch(Exception e){
			return Response.status(500).encoding(e.getMessage()).build();
		}
	}
	
	
	
	private void validarRequest(AgregarProductoAPedidoRequest request) {
		if( request.getIdPedido() == null){
			throw new RequestIncorrectoException("El id De pedido no debe ser null");
		}
		if(request.getIdVariante() == null){
			throw new RequestIncorrectoException("El id de variante no debe ser null");
		}		
	}

	private AgregarProductoAPedidoRequest toAgregarPedidoRequest(String agregarRequest) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		return mapper.readValue(agregarRequest, AgregarProductoAPedidoRequest.class);
	}

	private PedidoResponse toResponse(Pedido pedido) {
		return new PedidoResponse(pedido);
	}
	private String obtenerEmailDeContextoDeSeguridad(){
		return 	SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	

}
