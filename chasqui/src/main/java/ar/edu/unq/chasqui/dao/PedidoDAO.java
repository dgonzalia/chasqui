package ar.edu.unq.chasqui.dao;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import ar.edu.unq.chasqui.model.Pedido;

public interface PedidoDAO {

	public List<Pedido> obtenerPedidosProximosAVencer(Integer cantidadDeDias,Integer idVendedor,DateTime fechaCierrePedido);

	public void guardar(Pedido p);

	public List<Pedido> obtenerPedidos(Integer idVendedor);

	public Integer obtenerTotalPaginasDePedidosParaVendedor(Integer id);

	public List<Pedido> obtenerPedidos(Integer id, Date desde, Date hasta, String estadoSeleccionado);

}
