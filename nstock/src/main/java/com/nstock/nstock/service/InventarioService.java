package com.nstock.nstock.service; 

import com.nstock.nstock.entity.MotivoMerma;
import com.nstock.nstock.entity.MovimientoInventario;
import com.nstock.nstock.entity.Inventario;
import com.nstock.nstock.entity.InventarioId;
import com.nstock.nstock.entity.Producto; // <--- IMPORTACIÓN DE LA ENTIDAD
import com.nstock.nstock.repository.InventarioRepository;
import com.nstock.nstock.repository.MovimientoInventarioRepository;
import com.nstock.nstock.repository.ProductoRepository; // <--- IMPORTACIÓN DEL REPOSITORIO

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ProductoRepository productoRepository; // <--- INYECCIÓN DEL REPOSITORIO DE PRODUCTOS

    @Autowired
    private MovimientoInventarioRepository movimientoInventarioRepository;
    
    @Transactional
    public boolean modificarStockManual(Integer idProducto, Integer nuevoStock) { 
        InventarioId idCompuesto = new InventarioId(idProducto, 1); 
        Optional<Inventario> inventarioOpt = inventarioRepository.findById(idCompuesto);

        if (inventarioOpt.isPresent()) {
            Inventario inv = inventarioOpt.get();
            inv.setStockActual(nuevoStock);
            inventarioRepository.save(inv);
            return true;
        }
        return false;
    }

@Transactional
    public boolean ajustarStockRapido(String inputCodigo, Integer cantidad, String tipoAjuste, MotivoMerma motivo, String comentario) {
        
        Optional<Producto> productoOpt = productoRepository.findByCodigoBarras(inputCodigo);

        if (!productoOpt.isPresent()) {
            try {
                Integer idInterno = Integer.parseInt(inputCodigo);
                productoOpt = productoRepository.findById(idInterno);
            } catch (NumberFormatException e) {}
        }

        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            InventarioId idCompuesto = new InventarioId(producto.getIdProducto(), 1);
            Optional<Inventario> invOpt = inventarioRepository.findById(idCompuesto);

            if (invOpt.isPresent()) {
                Inventario inv = invOpt.get();
                int stockActual = inv.getStockActual();

                // REGLA MATEMÁTICA: ¿Es Merma o Ingreso?
                if ("MERMA".equalsIgnoreCase(tipoAjuste)) {
                    if (stockActual < cantidad) {
                        return false; // Bloquea si intentan sacar más de lo que hay
                    }
                    inv.setStockActual(stockActual - cantidad);
                } else {
                    inv.setStockActual(stockActual + cantidad);
                }
                
                inventarioRepository.save(inv);

                // TRAZABILIDAD: Guardamos el comprobante en el historial
                MovimientoInventario mov = new MovimientoInventario();
                mov.setIdProducto(producto.getIdProducto());
                mov.setIdSucursal(1);
                mov.setTipoMovimiento(tipoAjuste.toUpperCase()); // "INGRESO" o "MERMA"
                mov.setCantidad(cantidad);
                mov.setFechaHora(java.time.LocalDateTime.now());
                mov.setMotivoMerma("MERMA".equalsIgnoreCase(tipoAjuste) ? motivo : MotivoMerma.INGRESO_NORMAL);
                mov.setComentario(comentario);
                mov.setDescripcion("Ajuste rápido desde panel");
                
                movimientoInventarioRepository.save(mov);

                return true;
            }
        }
        return false;
    }
}