package ar.edu.unq.chasqui.services.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.chasqui.dao.PedidoDAO;
import ar.edu.unq.chasqui.model.Pedido;
import ar.edu.unq.chasqui.services.interfaces.PedidoService;

public class PedidoServiceImpl implements PedidoService {

	
	@Autowired
	private PedidoDAO pedidoDAO;
	
	@Override
	public List<Pedido> obtenerPedidosProximosAVencer(Integer cantidadDeDias,Integer idVendedor,DateTime fechaCierrePedido) {
		return pedidoDAO.obtenerPedidosProximosAVencer(cantidadDeDias,idVendedor,fechaCierrePedido);
	}

	@Override
	public void guardar(Pedido p) {
		pedidoDAO.guardar(p);
		
	}

	

}
