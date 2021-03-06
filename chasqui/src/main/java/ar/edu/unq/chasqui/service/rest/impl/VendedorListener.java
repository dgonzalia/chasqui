package ar.edu.unq.chasqui.service.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unq.chasqui.exceptions.VendedorInexistenteException;
import ar.edu.unq.chasqui.model.Vendedor;
import ar.edu.unq.chasqui.service.rest.response.ChasquiError;
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
			return Response.status(500).entity(new ChasquiError (e.getMessage())).build();
		}
	}
	
	
	@GET
	@Path("/{nombreVendedor}")
	@Produces("application/json")
	public Response obtenerVendedorPor(@PathParam("nombreVendedor")String nombreVendedor){
		try{
			return Response.ok(new VendedorResponse(vendedorService.obtenerVendedor(nombreVendedor))).build();
		}catch(VendedorInexistenteException e){
			return Response.status(406).entity(new ChasquiError(e.getMessage())).build();
		}catch(Exception e){			
			return Response.status(500).entity(new ChasquiError(e.getMessage())).build();
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
