package ar.edu.unq.chasqui.service.rest.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ar.edu.unq.chasqui.exceptions.DireccionesInexistentes;
import ar.edu.unq.chasqui.exceptions.RequestIncorrectoException;
import ar.edu.unq.chasqui.exceptions.UsuarioExistenteException;
import ar.edu.unq.chasqui.model.Cliente;
import ar.edu.unq.chasqui.model.Direccion;
import ar.edu.unq.chasqui.model.Notificacion;
import ar.edu.unq.chasqui.service.rest.request.DireccionEditRequest;
import ar.edu.unq.chasqui.service.rest.request.DireccionRequest;
import ar.edu.unq.chasqui.service.rest.request.EditarPerfilRequest;
import ar.edu.unq.chasqui.service.rest.response.ChasquiError;
import ar.edu.unq.chasqui.service.rest.response.DireccionResponse;
import ar.edu.unq.chasqui.service.rest.response.NotificacionResponse;
import ar.edu.unq.chasqui.service.rest.response.PerfilResponse;
import ar.edu.unq.chasqui.services.interfaces.UsuarioService;

@Service
@Path("/adm")
public class UsuarioListener {

	
	@Autowired
	UsuarioService usuarioService;
	
	@GET
	@Path("/read")
	@Produces("application/json")
	public Response obtenerDatosPerfilUsuario(@Context HttpHeaders header){
		try{	
			String email = obtenerEmailDeContextoDeSeguridad();
			Cliente c =  usuarioService.obtenerClienteConDireccion(email);
			return Response.ok(toResponse(c),MediaType.APPLICATION_JSON).build();			
		}catch(IndexOutOfBoundsException | UsuarioExistenteException e){
			return Response.status(406).entity(new ChasquiError("El email es invalido o el usuario no existe")).build();
		}catch(Exception e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	
	@PUT
	@Path("/registrar/{dispositivo}")
	@Produces("application/json")
	public Response registrarDispositivo(@PathParam("dispositivo") String dispositivo){
		String mail = obtenerEmailDeContextoDeSeguridad();
		usuarioService.agregarIDDeDispositivo(mail,dispositivo);
		return Response.ok().build();
	}
	
	@PUT
	@Path("/edit")
	@Produces("application/json")
	public Response editarPerfilUsuario(@Multipart(value="editRequest", type="application/json")final String editRequest){
		try{
			String email = obtenerEmailDeContextoDeSeguridad();
			EditarPerfilRequest request = toRequest(editRequest);
			usuarioService.modificarUsuario(request,email);
		}catch(IOException | UsuarioExistenteException e){
			return Response.status(406).entity(new ChasquiError("Parametros incorrectos")).build();
		}catch(Exception e){
			return Response.status(500).entity(new ChasquiError(e.getMessage())).build();
		}
		return Response.ok().build();
	}
	
	
	
	
	@GET
	@Path("/dir")
	@Produces("application/json")
	public Response obtenerDirecciones(@Context HttpHeaders header){
		try{
			String email = obtenerEmailDeContextoDeSeguridad();
			return Response.ok(toDireccionResponse(usuarioService.obtenerDireccionesDeUsuarioCon(email)),MediaType.APPLICATION_JSON).build();			
		}catch(IndexOutOfBoundsException | NullPointerException | UsuarioExistenteException e){
			return Response.status(406).entity(new ChasquiError("Parametros Incorrectos")).build();
		}catch(Exception e){
			return Response.status(500).entity(new ChasquiError(e.getMessage())).build();
		}
	}
	
	
	@POST
	@Path("/dir")
	@Produces("application/json")
	public Response agregarNuevaDireccion(@Multipart(value="direccionRequest", type="application/json")final String direccionRequest){
		try{
			DireccionRequest request = toDireccionRequest(direccionRequest);
			String mail = obtenerEmailDeContextoDeSeguridad();
			Integer id = usuarioService.agregarDireccionAUsuarioCon(mail,request);
			return Response.ok(id).build();
		}catch(IOException | RequestIncorrectoException e){
			return Response.status(406).entity(new ChasquiError("Parametros Incorrectos")).build();
		}catch(Exception e){
			return Response.status(500).entity(new ChasquiError(e.getMessage())).build();
		}
	}
	
	
	@POST
	@Path("/dir/edit")
	@Produces("application/json")
	public Response editarDireccionDe(@Multipart(value="direccionRequest",type="application/json")String request){
		try{
			String mail = obtenerEmailDeContextoDeSeguridad();
			DireccionEditRequest dirRequest = toDireccionEditRequest(request);
			usuarioService.editarDireccionDe(mail,dirRequest,dirRequest.getIdDireccion());
			return Response.ok().build();			
		}catch(RequestIncorrectoException e){
			return Response.status(406).entity(new ChasquiError("Parametros Incorrectos")).build();
		}catch(DireccionesInexistentes | UsuarioExistenteException e){
			return Response.status(404).entity(new ChasquiError("No se han encontrado direcciones")).build();
		}catch(Exception e){
			return Response.status(500).entity(new ChasquiError(e.getMessage())).build();
		}
	}
	
	private DireccionEditRequest toDireccionEditRequest(String req)throws IOException {
		DireccionEditRequest request = new DireccionEditRequest();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		request = mapper.readValue(req, DireccionEditRequest.class);
		return request;
	}


	@GET
	@Path("/dir/{idDireccion : \\d+}")
	@Produces("application/json")
	public Response elimiarDireccionDe(@PathParam("idDireccion")Integer idDireccion){
		try{
			String mail = obtenerEmailDeContextoDeSeguridad();
			usuarioService.eliminarDireccionDe(mail,idDireccion);
			return Response.ok().build();
		}catch(DireccionesInexistentes | UsuarioExistenteException e){
			return Response.status(404).entity(new ChasquiError("No se han encontrado direcciones")).build();
		}catch(Exception e){
			return Response.status(500).entity(new ChasquiError(e.getMessage())).build();
		}
		
	}
	
	
	@GET
	@Path("/notificacion/{pagina : \\d+}")
	@Produces("application/json")
	public Response obtenerNotificacionesDe(@PathParam("pagina")Integer pagina){
		try{
			String mail = obtenerEmailDeContextoDeSeguridad();			
			return Response.ok(toNotificacionResponse(usuarioService.obtenerNotificacionesDe(mail,pagina)),MediaType.APPLICATION_JSON).build();
		}catch(Exception e){
			return Response.status(500).entity(e.getMessage()).build();
		}
		
	}
	
	
	@POST
	@Path("/notificacion")
	@Produces("application/json")
	public Response enviarNotificacionDeInvitacion(@HeaderParam("destino")String destino){
		try{
			String origen = obtenerEmailDeContextoDeSeguridad();
			usuarioService.enviarInvitacionRequest(origen,destino);
			return Response.ok().build();			
		}catch(Exception e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	
	
	private List<NotificacionResponse> toNotificacionResponse(List<Notificacion> notificaciones) {
		List<NotificacionResponse>resultado= new ArrayList<NotificacionResponse>();
		for(Notificacion n : notificaciones){
			resultado.add(new NotificacionResponse(n));
		}
		return resultado;
	}



	private String obtenerEmailDeContextoDeSeguridad(){
		return 	SecurityContextHolder.getContext().getAuthentication().getName();
	}


	private List<DireccionResponse> toDireccionResponse(List<Direccion> dirs) {
		List<DireccionResponse>rs = new ArrayList<DireccionResponse>();
		for(Direccion d : dirs){
			rs.add(new DireccionResponse(d));
		}
		return rs;
	}

	private EditarPerfilRequest toRequest(String editRequest) throws IOException {
		EditarPerfilRequest request = new EditarPerfilRequest();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		request = mapper.readValue(editRequest, EditarPerfilRequest.class);
		return request;
	}
	
	private PerfilResponse toResponse(Cliente c) {
		return new PerfilResponse(c);
	}
	
	
	private DireccionRequest toDireccionRequest(String req)throws IOException{
		DireccionRequest request = new DireccionRequest();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		request = mapper.readValue(req, DireccionRequest.class);
		return request;
	}
	
}
