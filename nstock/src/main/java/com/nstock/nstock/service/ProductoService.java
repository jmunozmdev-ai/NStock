package com.nstock.nstock.service;

import com.nstock.nstock.entity.Inventario;
import com.nstock.nstock.entity.Producto;
import com.nstock.nstock.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- Importación añadida para las ediciones
import java.util.Optional;
import com.nstock.nstock.repository.InventarioRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;



    @Autowired
    private InventarioRepository inventarioRepository; // <--- AGREGA SOLO ESTA LÍNEA

    public Producto buscarPorCodigo(String codigoBarras) {
        Optional<Producto> producto = productoRepository.findByCodigoBarras(codigoBarras);
        
        if(producto.isPresent()){
            return producto.get();
        } else {
            throw new RuntimeException("Producto no encontrado");
        }
    }
    public Producto buscarPorId(Integer id) {
        Optional<Producto> producto = productoRepository.findById(id);
        return producto.orElse(null); // Retorna el producto si existe, o nulo si no
    }

@Transactional
    public Producto guardarProducto(Producto producto) {
        // 1. Guardamos el producto en el catálogo (Tabla Productos)
        Producto nuevoProducto = productoRepository.save(producto);

        // 2. Creamos el registro automático para la bodega (Tabla Inventario)
        Inventario nuevoInventario = new Inventario();
        
        // OPCIÓN A: Si tu entidad Inventario recibe el objeto Producto completo
        //nuevoInventario.setProducto(nuevoProducto);
        
        // OPCIÓN B: Si tu entidad Inventario guarda solo el ID numérico directo
        nuevoInventario.setIdProducto(nuevoProducto.getIdProducto());

        nuevoInventario.setIdSucursal(1);           // Asignamos la sucursal 1 por defecto
        nuevoInventario.setStockActual(0);          // Iniciamos con cero unidades

        // 3. Guardamos el registro en la tabla de inventarios
        inventarioRepository.save(nuevoInventario);

        return nuevoProducto;
    }

    // ==========================================
    // NUEVO MÉTODO PARA EDITAR NOMBRE Y PRECIO
    // ==========================================
    @Transactional
    public boolean editarNombreYPrecio(Integer idProducto, String nuevoNombre, Integer nuevoPrecio) {
        Optional<Producto> productoOpt = productoRepository.findById(idProducto);
        
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            producto.setNombre(nuevoNombre);
            producto.setPrecio(nuevoPrecio); // Mantenemos tu lógica de precios como números enteros
            productoRepository.save(producto);
            return true;
        }
        return false;
    }
}