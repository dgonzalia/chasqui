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
import ar.edu.unq.chasqui.model.Categoria;
import ar.edu.unq.chasqui.service.rest.response.CategoriaResponse;
import ar.edu.unq.chasqui.services.interfaces.CategoriaService;

@Service
@Path("/categoria")
public class CategoriaListener {

	@Autowired
	CategoriaService categoriaService;
	
	
	
	@GET
	@Path("/all/{idVendedor : \\d+ }")
	@Produces("application/json")
	public Response obtenerCategoriasDe(@PathParam("idVendedor")final Integer idVendedor){
		try{
			return Response.ok(toResponse(categoriaService.obtenerCategoriasDe(idVendedor)),MediaType.APPLICATION_JSON).build();
		}catch(VendedorInexistenteException e){
			return Response.status(404).entity("Vendedor inexistente o el mismo no posee categorias definidas").build();
		}catch(Exception e){
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
	
	private List<CategoriaResponse>toResponse(List<Categoria>css){
		List<CategoriaResponse> response = new ArrayList<CategoriaResponse>();
		for(Categoria c : css){
			response.add(new CategoriaResponse(c));
		}
		return response;
	}
	
}
