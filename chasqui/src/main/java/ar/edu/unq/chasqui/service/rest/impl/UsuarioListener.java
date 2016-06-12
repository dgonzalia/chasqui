package ar.edu.unq.chasqui.service.rest.impl;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unq.chasqui.exceptions.UsuarioExistenteException;
import ar.edu.unq.chasqui.model.Cliente;
import ar.edu.unq.chasqui.service.rest.request.EditarPerfilRequest;
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
			return Response.ok(toResponse(c),MediaType.APPLICATION_JSON).build();			
		}catch(IndexOutOfBoundsException | UsuarioExistenteException e){
			return Response.status(406).entity("El email es invalido o el usuario no existe").build();
		}catch(Exception e){
			return Response.status(500).entity(e.getMessage()).build();
		}
		
	}
	
	
	private PerfilResponse toResponse(Cliente c) {
		return new PerfilResponse(c);
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


	private EditarPerfilRequest toRequest(String editRequest) throws IOException {
		EditarPerfilRequest request = new EditarPerfilRequest();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		request = mapper.readValue(editRequest, EditarPerfilRequest.class);
		return request;
	}
	
	
}
