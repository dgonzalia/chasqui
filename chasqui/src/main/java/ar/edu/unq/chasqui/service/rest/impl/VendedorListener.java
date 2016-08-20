package ar.edu.unq.chasqui.service.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unq.chasqui.model.Vendedor;
import ar.edu.unq.chasqui.service.rest.response.VendedorResponse;
import ar.edu.unq.chasqui.services.interfaces.VendedorService;

@Service
@Path("/vendedor")
public class VendedorListener {

	
	@Autowired
	VendedorService vendedorService;
	
	
	
	
	@GET
	@Path("/all")
	@Produces("application/json")
	public Response obtenerVendedores(){
		try{
			return Response.ok(toResponse(vendedorService.obtenerVendedores()),MediaType.APPLICATION_JSON).build();
		}catch(Exception e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}




	private List<VendedorResponse> toResponse(List<Vendedor> vendedores) {
		List<VendedorResponse> response = new ArrayList<VendedorResponse>();
		for(Vendedor v : vendedores){
			response.add(new VendedorResponse(v));
		}
		return response;
	}
	
}
