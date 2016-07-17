package ar.edu.unq.chasqui.service.rest.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
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

import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unq.chasqui.exceptions.DireccionesInexistentes;
import ar.edu.unq.chasqui.exceptions.RequestIncorrectoException;
import ar.edu.unq.chasqui.exceptions.UsuarioExistenteException;
import ar.edu.unq.chasqui.model.Cliente;
import ar.edu.unq.chasqui.model.Direccion;
import ar.edu.unq.chasqui.service.rest.request.DireccionRequest;
import ar.edu.unq.chasqui.service.rest.request.EditarPerfilRequest;
import ar.edu.unq.chasqui.service.rest.response.DireccionResponse;
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
	public Response obtenerDatosPerfilUsuario(@Context HttpHeaders header ){
		try{			
			String email = String.valueOf(header.getRequestHeader("mail").get(0));
			Cliente c = (Cliente) usuarioService.obtenerUsuarioPorEmail(email);
			if(c == null){
				throw new UsuarioExistenteException();
			}
			return Response.ok(toResponse(c),MediaType.APPLICATION_JSON).build();			
		}catch(IndexOutOfBoundsException | UsuarioExistenteException e){
			return Response.status(406).entity("El email es invalido o el usuario no existe").build();
		}catch(Exception e){
			return Response.status(500).entity(e.getMessage()).build();
		}
		
	}
	
	@PUT
	@Path("/edit")
	@Produces("application/json")
	public Response editarPerfilUsuario(@Multipart(value="editRequest", type="application/json")final String editRequest){
		try{
			EditarPerfilRequest request = toRequest(editRequest);
			usuarioService.modificarUsuario(request);
		}catch(IOException | UsuarioExistenteException e){
			return Response.status(406).entity("Parametros incorrectos").build();
		}catch(Exception e){
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}
	
	
	
	@GET
	@Path("/dir")
	@Produces("application/json")
	public Response obtenerDirecciones(@Context HttpHeaders header){
		try{
			String email = (String) header.getRequestHeader("mail").get(0);
			return Response.ok(toDireccionResponse(usuarioService.obtenerDireccionesDeUsuarioCon(email))).build();			
		}catch(IndexOutOfBoundsException | NullPointerException | UsuarioExistenteException e){
			return Response.status(406).entity("Parametros Incorrectos").build();
		}catch(Exception e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	
	@POST
	@Path("/dir")
	@Produces("application/json")
	public Response agregarNuevaDireccion(@HeaderParam("mail") String mail,
			@Multipart(value="direccionRequest", type="application/json")final String direccionRequest){
		try{
			DireccionRequest request = toDireccionRequest(direccionRequest);
			validarDireccionRequest(request);
			usuarioService.agregarDireccionAUsuarioCon(mail,request);
			return Response.ok().build();
		}catch(IOException | RequestIncorrectoException e){
			return Response.status(406).entity("Parametros Incorrectos").build();
		}catch(Exception e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	
	@PUT
	@Path("/dir/{idDireccion}")
	@Produces("application/json")
	public Response editarDireccionDe(@HeaderParam("mail") String mail,
			@Multipart(value="direccionRequest",type="application/json")String request,@PathParam("idDireccion")Integer idDireccion){
		try{
			DireccionRequest dirRequest = toDireccionRequest(request);
			validarDireccionRequest(dirRequest);
			usuarioService.editarDireccionDe(mail,dirRequest,idDireccion);
			return Response.ok("Se ha registrado la modificacion de la direccion correctamente").build();			
		}catch(RequestIncorrectoException e){
			return Response.status(406).entity("Parametros Incorrectos").build();
		}catch(DireccionesInexistentes | UsuarioExistenteException e){
			return Response.status(404).entity("No se han encontrado direcciones").build();
		}catch(Exception e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	@DELETE
	@Path("/dir/{idDireccion}")
	@Produces("application/json")
	public Response elimiarDireccionDe(@HeaderParam("user")String mail,@PathParam("idDireccion")Integer idDireccion){

		try{
			usuarioService.eliminarDireccionDe(mail,idDireccion);
			return Response.ok("Se ha eliminado la direccion correctamente.").build();
		}catch(DireccionesInexistentes | UsuarioExistenteException e){
			return Response.status(404).entity("No se han encontrado direcciones").build();
		}catch(Exception e){
			return Response.status(500).entity(e.getMessage()).build();
		}
		
	}
	


	private void validarDireccionRequest(DireccionRequest request) {
		if(StringUtils.isEmpty(request.getAlias())){
			throw new RequestIncorrectoException();
		}
		if(StringUtils.isEmpty(request.getCalle())){
			throw new RequestIncorrectoException();
		}
		if(StringUtils.isEmpty(request.getCodigoPostal())){
			throw new RequestIncorrectoException();
		}
		if(request.getPredeterminada() == null){
			throw new RequestIncorrectoException();
		}
		if(StringUtils.isEmpty(request.getLocalidad())){
			throw new RequestIncorrectoException();
		}
		if(request.getAltura() == null || request.getAltura() < 0){
			throw new RequestIncorrectoException();
		}
		
		
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
