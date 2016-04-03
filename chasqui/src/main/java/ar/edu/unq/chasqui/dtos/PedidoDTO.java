package ar.edu.unq.chasqui.dtos;

public class PedidoDTO {
		 private String usuario;
		 private String nombreProducto;
		 private String nombreVariante;
		 private Integer cantidad;
		 private Double precio;
		
		
		
		
		public PedidoDTO(String user,String producto,String variante,Integer cant,Double prec){
			usuario = user;
			nombreProducto = producto;
			nombreVariante = variante;
			cantidad = cant;
			precio = prec;
		}

		public String getUsuario() {
			return usuario;
		}
		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}
		public String getNombreProducto() {
			return nombreProducto;
		}
		public void setNombreProducto(String nombreProducto) {
			this.nombreProducto = nombreProducto;
		}
		public String getNombreVariante() {
			return nombreVariante;
		}
		public void setNombreVariante(String nombreVariante) {
			this.nombreVariante = nombreVariante;
		}
		public Integer getCantidad() {
			return cantidad;
		}
		public void setCantidad(Integer cantidad) {
			this.cantidad = cantidad;
		}
		public Double getPrecio() {
			return precio;
		}
		public void setPrecio(Double precio) {
			this.precio = precio;
		}	
}
