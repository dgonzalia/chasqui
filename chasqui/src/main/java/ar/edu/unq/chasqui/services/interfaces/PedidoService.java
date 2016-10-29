package ar.edu.unq.chasqui.services.interfaces;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.chasqui.model.Pedido;

public interface PedidoService {

	
	@Transactional
	public List<Pedido>obtenerPedidosProximosAVencer(Integer cantidadDeDias,Integer idVendedor,DateTime fechaCierrePedido);
	public List<Pedido>obtenerPedidosDeVendedor(Integer idVendedor);

	public void guardar(Pedido p);
	public int totalPedidosParaVendedor(Integer id);
	public List<Pedido> obtenerPedidosDeVendedor(Integer id, Date desde, Date hasta, String estadoSeleccionado);
	
}
