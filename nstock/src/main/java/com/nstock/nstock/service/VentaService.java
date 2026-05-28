package com.nstock.nstock.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        venta.setMedioPago(request.getMedioPago());
        venta.setFechaHora(LocalDateTime.now());
        venta.setTotal(0); 
        venta = ventaRepository.save(venta);

        int totalVenta = 0;

        // PREPARAR LISTAS PARA GUARDADO EN BLOQUE (Optimización de velocidad)
        List<Inventario> inventariosAActualizar = new ArrayList<>();
        List<MovimientoInventario> movimientosAGuardar = new ArrayList<>();
        List<DetalleVenta> detallesAGuardar = new ArrayList<>();

        // 2. Abrir la caja y procesar cada productoz   
        for (DetalleVentaDTO item : request.getDetalles()) {

            Producto producto = productoRepository.findById(item.getIdProducto())
                    .orElseThrow(() -> new RuntimeException(
                    "Producto no encontrado: ID " + item.getIdProducto()));

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

            // Restar stock y agregar a la lista, NO guardar todavía
            inventario.setStockActual(inventario.getStockActual() - item.getCantidad());
            inventariosAActualizar.add(inventario);

            // Preparar movimiento y agregar a la lista
            MovimientoInventario movimiento = new MovimientoInventario();
            movimiento.setIdProducto(item.getIdProducto());
            movimiento.setIdSucursal(request.getIdSucursal());
            movimiento.setTipoMovimiento("VENTA");
            movimiento.setCantidad(item.getCantidad());
            movimiento.setFechaHora(LocalDateTime.now());
            movimiento.setDescripcion("Venta registrada en boleta #" + venta.getIdVenta());
            movimientosAGuardar.add(movimiento);

            // Calcular subtotal, preparar detalle y agregar a la lista
            int subtotal = producto.getPrecio() * item.getCantidad();
            totalVenta += subtotal;

            DetalleVenta detalle = new DetalleVenta();
            detalle.setIdVenta(venta.getIdVenta());
            detalle.setIdProducto(producto.getIdProducto());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(subtotal);
            detallesAGuardar.add(detalle);
        }

        // 3. ¡EL GOLPE FINAL! Guardar todas las listas de una sola vez
        inventarioRepository.saveAll(inventariosAActualizar);
        movimientoRepository.saveAll(movimientosAGuardar);
        detalleVentaRepository.saveAll(detallesAGuardar);

        // 4. Poner el gran total en la boleta final y actualizar
        venta.setTotal(totalVenta);
        return ventaRepository.save(venta);
    }
}