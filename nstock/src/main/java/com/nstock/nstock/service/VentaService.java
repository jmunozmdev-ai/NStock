package com.nstock.nstock.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nstock.nstock.dto.DetalleVentaDTO;
import com.nstock.nstock.dto.VentaRequestDTO;
import com.nstock.nstock.entity.DetalleVenta;
import com.nstock.nstock.entity.Inventario;
import com.nstock.nstock.entity.InventarioId;
import com.nstock.nstock.entity.MovimientoInventario;
import com.nstock.nstock.entity.Producto;
import com.nstock.nstock.entity.Venta;
import com.nstock.nstock.repository.DetalleVentaRepository;
import com.nstock.nstock.repository.InventarioRepository;
import com.nstock.nstock.repository.MovimientoInventarioRepository;
import com.nstock.nstock.repository.ProductoRepository;
import com.nstock.nstock.repository.VentaRepository;

@Service
public class VentaService {

    @Autowired
    private MovimientoInventarioRepository movimientoRepository;
    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private InventarioRepository inventarioRepository;

    @Transactional // ¡La palabra mágica de seguridad!
    public Venta registrarVenta(VentaRequestDTO request) {

        // 1. Crear la boleta principal (Venta) vacía para obtener su ID
        Venta venta = new Venta();
        venta.setIdSucursal(request.getIdSucursal());
        venta.setIdUsuario(request.getIdUsuario());

        // NUEVO CAMPO
        venta.setMedioPago(request.getMedioPago());

        venta.setFechaHora(LocalDateTime.now());
        venta.setTotal(0); // Empezamos en 0, lo sumaremos al final
        venta = ventaRepository.save(venta);

        int totalVenta = 0;

        // 2. Abrir la caja y procesar cada producto que envió la aplicación móvil
        for (DetalleVentaDTO item : request.getDetalles()) {

            // a. Buscar el producto real para asegurar que el precio sea el correcto
            Producto producto = productoRepository.findById(item.getIdProducto())
                    .orElseThrow(() -> new RuntimeException(
                    "Producto no encontrado: ID " + item.getIdProducto()));

            // b. Ir a la bodega (Inventario) a revisar si hay stock en ESA sucursal
            InventarioId invId = new InventarioId(
                    item.getIdProducto(),
                    request.getIdSucursal()
            );

            Inventario inventario = inventarioRepository.findById(invId)
                    .orElseThrow(() -> new RuntimeException(
                    "El producto no está en el inventario de esta sucursal"));

            if (inventario.getStockActual() < item.getCantidad()) {
                throw new RuntimeException(
                        "Stock insuficiente para: " + producto.getNombre());
            }

            // c. ¡Descontar el stock de la repisa!
            inventario.setStockActual(
                    inventario.getStockActual() - item.getCantidad());

            inventarioRepository.save(inventario);

            // d. Registrar el historial del movimiento
            MovimientoInventario movimiento = new MovimientoInventario();
            movimiento.setIdProducto(item.getIdProducto());
            movimiento.setIdSucursal(request.getIdSucursal());
            movimiento.setTipoMovimiento("VENTA");
            movimiento.setCantidad(item.getCantidad());
            movimiento.setFechaHora(LocalDateTime.now());
            movimiento.setDescripcion(
                    "Venta registrada en boleta #" + venta.getIdVenta());

            movimientoRepository.save(movimiento);

            // e. Calcular el subtotal y anotarlo en el detalle de la boleta
            int subtotal = producto.getPrecio() * item.getCantidad();
            totalVenta += subtotal;

            DetalleVenta detalle = new DetalleVenta();
            detalle.setIdVenta(venta.getIdVenta());
            detalle.setIdProducto(producto.getIdProducto());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(subtotal);

            detalleVentaRepository.save(detalle);
        }

        // 3. Poner el gran total en la boleta final y guardarla
        venta.setTotal(totalVenta);

        return ventaRepository.save(venta);
    }
}
