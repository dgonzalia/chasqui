package ar.edu.unq.chasqui.service.rest.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unq.chasqui.model.Producto;
import ar.edu.unq.chasqui.service.rest.request.ProductoRequest;
import ar.edu.unq.chasqui.service.rest.response.ProductoResponse;
import ar.edu.unq.chasqui.services.interfaces.ProductoService;

@Service
@Path("/producto")
public class ProductoListener {

	@Autowired
	ProductoService productoService;

	
	@POST
	@Path("/all")
	@Produces("application/json")
	public Response obtenerTodosLosProductos(@Multipart(value="productoRequest", type="application/json")final String productoRequest){
		try{
			ProductoRequest request = toRequest(productoRequest);	
			return toResponse(productoService.obtenerProductosPorCategoria(request.getIdCategoria(),request.getPagina(),request.getCantItems()));
		}catch(IOException e){
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
	
	
	private ProductoRequest toRequest(String request) throws IOException{
		ProductoRequest prodRequest = new ProductoRequest();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		prodRequest = mapper.readValue(request, ProductoRequest.class);
		return prodRequest;
	}
	
	
	
	
}
