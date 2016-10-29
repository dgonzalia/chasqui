package ar.edu.unq.chasqui.services.impl;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unq.chasqui.aspect.Auditada;
import ar.edu.unq.chasqui.dao.PedidoDAO;
import ar.edu.unq.chasqui.model.Pedido;
import ar.edu.unq.chasqui.services.interfaces.PedidoService;

@Auditada
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

	@Override
	public List<Pedido> obtenerPedidosDeVendedor(Integer idVendedor) {
		return pedidoDAO.obtenerPedidos(idVendedor);
	}

	@Override
	public int totalPedidosParaVendedor(Integer id) {
		return pedidoDAO.obtenerTotalPaginasDePedidosParaVendedor(id);
	}

	@Override
	public List<Pedido> obtenerPedidosDeVendedor(Integer id, Date desde, Date hasta, String estadoSeleccionado) {
		return pedidoDAO.obtenerPedidos(id,desde,hasta,estadoSeleccionado);
		
	}

	

}
