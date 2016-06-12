package ar.edu.unq.chasqui.service.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unq.chasqui.model.Producto;
import ar.edu.unq.chasqui.service.rest.response.ProductoResponse;
import ar.edu.unq.chasqui.services.interfaces.ProductoService;

@Service
@Path("/producto")
public class ProductoListener {

	@Autowired
	ProductoService productoService;

	
	@GET
	@Path("/all")
	@Produces("application/json")
	public Response obtenerTodosLosProductos(@Context HttpHeaders header ){
		
		try{
			Integer pagina = Integer.valueOf(header.getRequestHeader("pagina").get(0));
			Integer cantidadDeItems = Integer.valueOf(header.getRequestHeader("cantItems").get(0));
			Integer idCategoria = Integer.valueOf(header.getRequestHeader("idCategoria").get(0));			
			
			return toResponse(productoService.obtenerProductos(idCategoria,pagina,cantidadDeItems));
		}catch(NumberFormatException | IndexOutOfBoundsException e){
			return Response.status(406).entity("Parametros incorrectos").build();
		}catch(Exception e){
			return Response.status(500).entity(e.getMessage()).build();
		}
		
		
	}


	private Response toResponse(List<Producto> productos) {
		List<ProductoResponse>response = new ArrayList<ProductoResponse>();
		for(Producto p : productos){
			response.add(new ProductoResponse(p));
		}
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}
	
	
	
	
}
