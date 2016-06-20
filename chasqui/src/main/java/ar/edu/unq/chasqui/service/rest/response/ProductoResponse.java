package ar.edu.unq.chasqui.service.rest.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ar.edu.unq.chasqui.model.Producto;
import ar.edu.unq.chasqui.model.Variante;

public class ProductoResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7556219779540939060L;

	private Integer cantItems;
	private Integer pagina;
	private List<VariedadResponse>productos = new ArrayList<VariedadResponse>();
	
	public ProductoResponse(){}
	
	public ProductoResponse (List<Producto> prod,Integer pag,Integer items,final String precio){
		cantItems = items;
		pagina = pag;
		for(Producto p : prod){
			for(Variante v : p.getVariantes()){
				productos.add(new VariedadResponse(v,p));				
			}
		}
		
		Collections.sort(productos, new Comparator<VariedadResponse>(){

			@Override
			public int compare(VariedadResponse o1, VariedadResponse o2) {
				if(precio.equals("Up")){
					return o1.getPrecio().compareTo(o2.getPrecio());
				}
				return o2.getPrecio().compareTo(o1.getPrecio());
			}
			
		});
	}
	


	public Integer getCantItems() {
		return cantItems;
	}


	public void setCantItems(Integer cantItems) {
		this.cantItems = cantItems;
	}


	public Integer getPagina() {
		return pagina;
	}


	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}


	public List<VariedadResponse> getProductos() {
		return productos;
	}


	public void setProductos(List<VariedadResponse> productos) {
		this.productos = productos;
	}
	
	
	
	
	
	
	
	
}
