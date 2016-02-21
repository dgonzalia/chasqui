package ar.edu.unq.chasqui.service.rest.impl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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
	
	
}
