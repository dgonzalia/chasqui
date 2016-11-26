package ar.edu.unq.chasqui.service.rest.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ar.edu.unq.chasqui.exceptions.PedidoInexistenteException;
import ar.edu.unq.chasqui.exceptions.PedidoVigenteException;
import ar.edu.unq.chasqui.exceptions.ProductoInexsistenteException;
import ar.edu.unq.chasqui.exceptions.RequestIncorrectoException;
import ar.edu.unq.chasqui.exceptions.UsuarioExistenteException;
import ar.edu.unq.chasqui.model.Pedido;
import ar.edu.unq.chasqui.service.rest.request.AgregarQuitarProductoAPedidoRequest;
import ar.edu.unq.chasqui.service.rest.request.ConfirmarPedidoRequest;
import ar.edu.unq.chasqui.service.rest.response.ChasquiError;
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
			return Response.status(406).entity(new ChasquiError(e.getMessage())).build();
		}catch(Exception e){
			return Response.status(500).entity(new ChasquiError(e.getMessage())).build();
		}
	}
	
	@GET
	@Produces("application/json")
	@Path("/individual/{idVendedor : \\d+}")
	public Response obtenerPedidoActual(@PathParam("idVendedor")Integer idVendedor){
		String mail =  obtenerEmailDeContextoDeSeguridad();
		try{
			return Response.ok(toResponse(usuarioService.obtenerPedidoActualDe(mail,idVendedor)),MediaType.APPLICATION_JSON).build();
		}catch(PedidoInexistenteException e){
			return Response.status(404).entity(new ChasquiError(e.getMessage())).build();
		}
		catch(Exception e){
			return Response.status(500).entity(new ChasquiError(e.getMessage())).build();
		}
	}	
	
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/vigentes")
	public Response obtenerPedidosVigentes(){
		String mail =  obtenerEmailDeContextoDeSeguridad();
		try{
			return Response.ok(toListResponse(usuarioService.obtenerPedidosVigentesDe(mail)),MediaType.APPLICATION_JSON).build();
		}catch(Exception e){
			return Response.status(500).entity(new ChasquiError(e.getMessage())).build();
		}
	}

	@PUT
	//@Consumes("application/json")
	@Produces("application/json")
	@Path("/individual")
	public Response agregarProductoAPedido(@Multipart(value="agregarRequest", type="application/json")final String agregarRequest){
		try{
			AgregarQuitarProductoAPedidoRequest request = toAgregarPedidoRequest(agregarRequest);
			String email = obtenerEmailDeContextoDeSeguridad();
			usuarioService.agregarPedidoA(request,email);
			return Response.ok().build();
		}catch(IOException | RequestIncorrectoException e ){
			return Response.status(406).entity(new ChasquiError("Parametros Incorrectos")).build();
		}catch(PedidoVigenteException | ProductoInexsistenteException e){
			return Response.status(404).entity(new ChasquiError(e.getMessage())).build();
		}catch(Exception e){
			return Response.status(500).entity(new ChasquiError(e.getMessage())).build();
		}
	}
	
	
	@DELETE
	@Produces("application/json")
	@Path("/individual")
	public Response eliminarProductoAlPedido(@Multipart(value="eliminarRequest", type="application/json") final String eliminarRequest){
		try{
			AgregarQuitarProductoAPedidoRequest request = toAgregarPedidoRequest(eliminarRequest);
			String email = obtenerEmailDeContextoDeSeguridad();
			usuarioService.eliminarProductoDePedido(request,email);
			return Response.ok().build();
		}catch(IOException | RequestIncorrectoException e){
			return Response.status(406).entity(new ChasquiError("Parametros Incorrectos")).build();
		}catch(Exception e){
			return Response.status(500).entity(new ChasquiError(e.getMessage())).build();
		}		
	}
	
	
	@DELETE
	@Produces("application/json")
	@Path("/individual/{idPedido : \\d+}")
	public Response eliminarPedido(@PathParam("idPedido")Integer idPedido){
		try{
			String email = obtenerEmailDeContextoDeSeguridad();
			usuarioService.eliminarPedidoPara(email,idPedido);
			return Response.ok().build();			
		}catch(RequestIncorrectoException e){
			return Response.status(406).entity(new ChasquiError("Parametros Incorrectos")).build();
		}catch(Exception e){
			return Response.status(500).entity(new ChasquiError(e.getMessage())).build();
		}	
	}
	
	@POST
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/confirmar")
	public Response confirmarPedido(ConfirmarPedidoRequest request){
		try{
			String email = obtenerEmailDeContextoDeSeguridad();
			usuarioService.confirmarPedido(email,request);
			return Response.ok().build();
		}catch(PedidoInexistenteException e){
			return Response.status(406).entity(new ChasquiError("Parametros Incorrectos")).build();
		}catch(Exception e){
			return Response.status(500).entity(new ChasquiError(e.getMessage())).build();
		}
	}

	

	private AgregarQuitarProductoAPedidoRequest toAgregarPedidoRequest(String agregarRequest) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		return mapper.readValue(agregarRequest, AgregarQuitarProductoAPedidoRequest.class);
	}

	private PedidoResponse toResponse(Pedido pedido) {
		return new PedidoResponse(pedido);
	}
	private String obtenerEmailDeContextoDeSeguridad(){
		return 	SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	
	private List<PedidoResponse> toListResponse(List<Pedido> obtenerPedidosVigentesDe) {
		List<PedidoResponse> resultado = new ArrayList<PedidoResponse>();
		for(Pedido p : obtenerPedidosVigentesDe){
			resultado.add(new PedidoResponse(p));
		}
		return resultado;
	}

	

}
