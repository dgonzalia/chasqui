package ar.edu.unq.chasqui.service.rest.impl;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.chasqui.exceptions.RequestIncorrectoException;
import ar.edu.unq.chasqui.exceptions.UsuarioExistenteException;
import ar.edu.unq.chasqui.model.Cliente;
import ar.edu.unq.chasqui.service.rest.request.LoginRequest;
import ar.edu.unq.chasqui.service.rest.request.SingUpRequest;
import ar.edu.unq.chasqui.service.rest.response.ChasquiError;
import ar.edu.unq.chasqui.service.rest.response.LoginResponse;
import ar.edu.unq.chasqui.services.impl.MailService;
import ar.edu.unq.chasqui.services.interfaces.UsuarioService;


@Path("/sso")
public class SingInSingUpListener{

	
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	MailService mailService; 
	
	
	@POST
	@Path("/singIn")
	@Produces("application/json")
	public Response logIn(@Multipart(value="loginRequest", type="application/json")final String loginRequest ){
		
		try{
			LoginRequest request = toLoginRequest(loginRequest);
			Cliente c = usuarioService.loginCliente(request.getEmail(), request.getPassword());
			return toLoginResponse(c);
		}catch(Exception e){
			return Response.status(401).entity(new ChasquiError(e.getMessage())).build();
		}
	}
	
	
	
	
	@POST
	@Path("/singUp")
	@Produces("application/json")
	public Response singUp(@Multipart(value="singUpRequest",type="application/json") final String singUpRequest) {
		try{
			SingUpRequest request = toSingUpRequest(singUpRequest);
			Cliente c = usuarioService.crearCliente(request);
			return toLoginResponse(c);
		}catch(RequestIncorrectoException | IOException e){
			return Response.status(406).entity(new ChasquiError("Debe completar todos los campos")).build();
		}catch(UsuarioExistenteException e){
			return Response.status(409).entity(new ChasquiError(e.getMessage())).build();
		}catch(Exception e){
			return Response.status(500).entity(new ChasquiError(e.getMessage())).build();
		}
	}
	
	
	
	@GET
	@Path("/resetPass/{email}")
	@Produces("application/json")
	public Response olvidoContraseña(@PathParam("email")final String email){
		
		try{
			mailService.enviarEmailRecuperoContraseñaCliente(email);
		}catch(UsuarioExistenteException e){
			return Response.status(406).entity(new ChasquiError("Email invalido")).build();
		}catch(Exception e){
			return Response.status(500).entity(new ChasquiError(e.getMessage())).build();
		}
		return Response.ok().build();
	}



	private SingUpRequest toSingUpRequest(String request) throws IOException {
		SingUpRequest singUpRequest = new SingUpRequest();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		singUpRequest = mapper.readValue(request, SingUpRequest.class);
		return singUpRequest;
	}
	
	
	private Response toLoginResponse(Cliente c) {
		LoginResponse response = new LoginResponse(c); 
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}



	private LoginRequest toLoginRequest(String request) throws IOException{
		LoginRequest loginRequest = new LoginRequest();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		loginRequest = mapper.readValue(request, LoginRequest.class);
		return loginRequest;
	}
}
