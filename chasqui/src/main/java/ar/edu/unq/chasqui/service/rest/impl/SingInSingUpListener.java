package ar.edu.unq.chasqui.service.rest.impl;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.codehaus.jackson.map.ObjectMapper;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.chasqui.exceptions.RequestIncorrectoException;
import ar.edu.unq.chasqui.exceptions.UsuarioExistenteException;
import ar.edu.unq.chasqui.model.Cliente;
import ar.edu.unq.chasqui.model.Usuario;
import ar.edu.unq.chasqui.service.rest.request.DireccionRequest;
import ar.edu.unq.chasqui.service.rest.request.LoginRequest;
import ar.edu.unq.chasqui.service.rest.request.SingUpRequest;
import ar.edu.unq.chasqui.service.rest.response.LoginResponse;
import ar.edu.unq.chasqui.services.interfaces.UsuarioService;


@Path("/sso")
public class SingInSingUpListener{

	
	@Autowired
	UsuarioService usuarioService;
	
	@POST
	@Path("/singIn")
	@Produces("application/json")
	public Response logIn(@Multipart(value="loginRequest", type="application/json")final String loginRequest ){
		
		try{
			LoginRequest request = toLoginRequest(loginRequest);
			Cliente c = usuarioService.loginCliente(request.getEmail(), request.getPassword());
			return toLoginResponse(c);
		}catch(Exception e){
			return Response.status(401).build();
		}
	}
	
	
	
	
	@POST
	@Path("/singUp")
	@Produces("application/json")
	public Response singUp(@Multipart(value="singUpRequest",type="application/json") final String singUpRequest){
		try{
			SingUpRequest request = toSingUpRequest(singUpRequest);
			validarRequestCreacionDeUsuario(request);
			Cliente c = usuarioService.crearCliente(request);
			return toLoginResponse(c);
		}catch(RequestIncorrectoException e){
			return Response.status(400).entity(e.getMessage()).build();
		}catch(UsuarioExistenteException e){
			return Response.status(409).entity(e.getMessage()).build();
		}catch(Exception e){
			return Response.status(500).build();
		}
	}
	
	
	
	
	
	
	
	
	
	





	private void validarRequestCreacionDeUsuario(SingUpRequest request) throws RequestIncorrectoException {
		if(StringUtil.isEmpty(request.getEmail())){
			throw new RequestIncorrectoException("Debe completar todos los campos");
		}
		
		if(StringUtil.isEmpty(request.getApellido())){
			throw new RequestIncorrectoException("Debe completar todos los campos");
		}
		
		if(StringUtil.isEmpty(request.getNickName())){
			throw new RequestIncorrectoException("Debe completar todos los campos");
		}
		
		if(StringUtil.isEmpty(request.getNombre())){
			throw new RequestIncorrectoException("Debe completar todos los campos");
		}
		if(StringUtil.isEmpty(request.getPassword())){
			throw new RequestIncorrectoException("Debe completar todos los campos");
		}
		if(request.getDireccion() == null){
			throw new RequestIncorrectoException("Debe completar todos los campos");
		}
		
		if(request.getTelefonoFijo() == null){
			throw new RequestIncorrectoException("Debe completar todos los campos");
		}
		
		
		validarDireccion(request.getDireccion());
		
		Usuario u = usuarioService.obtenerUsuarioPorEmail(request.getEmail());
		if(u != null){
			throw new UsuarioExistenteException();
		}
	}




	private void validarDireccion(DireccionRequest direccion) {
		if(StringUtils.isEmpty(direccion.getCalle())){
			throw new RequestIncorrectoException("Debe completar todos los campos");
		}
		
		if(StringUtils.isEmpty(direccion.getLocalidad())){
			throw new RequestIncorrectoException("Debe completar todos los campos");
		}
		
		if(StringUtils.isEmpty(direccion.getCodigoPostal())){
			throw new RequestIncorrectoException("Debe completar todos los campos");
		}
		
		if(direccion.getAltura() == null){
			throw new RequestIncorrectoException("Debe completar todos los campos");
		}
		if(direccion.getLongitud() == null){
			throw new RequestIncorrectoException("Debe completar todos los campos");
		}
		if(direccion.getLatitud() == null){
			throw new RequestIncorrectoException("Debe completar todos los campos");
		}
	}




	private SingUpRequest toSingUpRequest(String request) throws Exception{
		SingUpRequest singUpRequest = new SingUpRequest();
		ObjectMapper mapper = new ObjectMapper();
		singUpRequest = mapper.readValue(request, SingUpRequest.class);
		return singUpRequest;
	}
	
	
	private Response toLoginResponse(Cliente c) {
		LoginResponse response = new LoginResponse(c); 
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}



	private LoginRequest toLoginRequest(String request) throws Exception{
		LoginRequest loginRequest = new LoginRequest();
		ObjectMapper mapper = new ObjectMapper();
		loginRequest = mapper.readValue(request, LoginRequest.class);
		return loginRequest;
	}
}
