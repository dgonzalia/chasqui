package ar.edu.unq.chasqui.service.rest.impl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

@Service
@Path("/mockService")
public class MockServiceImpl {

	
	
	
	@GET
	@Path("/test/{num}")
	@Produces("application/json")
	public int test (@PathParam("num") final int num){
		return num;
	}
	
	
	
	/**
	 * De esta forma se obtienen parametros de el header!!
	 */
//	
//	@GET
//	@Path("/all")
//	@Produces("application/json")
//	public Response obtenerTodosLosProductos(@Context HttpHeaders header ){
//		try{
//			Integer pagina = Integer.valueOf(header.getRequestHeader("pagina").get(0));
//			Integer cantidadDeItems = Integer.valueOf(header.getRequestHeader("cantItems").get(0));
//			Integer idCategoria = Integer.valueOf(header.getRequestHeader("idCategoria").get(0));
//	
//	
}
