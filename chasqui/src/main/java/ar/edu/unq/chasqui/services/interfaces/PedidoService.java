package ar.edu.unq.chasqui.services.interfaces;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.chasqui.model.Pedido;

public interface PedidoService {

	
	@Transactional
	public List<Pedido>obtenerPedidosProximosAVencer(Integer cantidadDeDias,Integer idVendedor,DateTime fechaCierrePedido);

	public void guardar(Pedido p);
	
}
