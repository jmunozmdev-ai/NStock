package com.nstock.nstock.controller;

import com.nstock.nstock.entity.Inventario;
import com.nstock.nstock.entity.MotivoMerma;
import com.nstock.nstock.repository.InventarioRepository;
import com.nstock.nstock.repository.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

import com.nstock.nstock.service.InventarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private InventarioService inventarioService;
    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public List<Map<String, Object>> listarInventarioConNombres() {
        // Traemos todo el inventario
        List<Inventario> lista = inventarioRepository.findAll();
        
        // Creamos una lista más "bonita" con nombres para el navegador
        return lista.stream().map(inv -> {
            Map<String, Object> map = new HashMap<>();
            map.put("idProducto", inv.getIdProducto());
            map.put("stockActual", inv.getStockActual());
            
            // Buscamos el nombre del producto para la tabla
            productoRepository.findById(inv.getIdProducto()).ifPresent(p -> {
                map.put("nombreProducto", p.getNombre());
            });
            
            return map;
        }).collect(Collectors.toList());
    }
    @PutMapping("/{id}/stock")
    public ResponseEntity<?> actualizarStockManual(@PathVariable Integer id, @RequestBody Map<String, Integer> body) {
        Integer nuevoStock = body.get("nuevoStock");
        
        // Llamamos al servicio que acabas de terminar
        boolean exito = inventarioService.modificarStockManual(id, nuevoStock);
        
        if (exito) {
            return ResponseEntity.ok().body("{\"mensaje\": \"Stock actualizado\"}");
        } else {
            return ResponseEntity.status(404).body("{\"error\": \"No se encontró el producto\"}");
        }
    }
    
@PutMapping("/sumar-stock") 
    public ResponseEntity<?> ajustarStockRapido(@RequestBody Map<String, Object> payload) {
        try {
            String codigo = payload.get("codigo").toString();
            Integer cantidad = Integer.parseInt(payload.get("cantidad").toString());
            
            String tipoAjuste = payload.getOrDefault("tipoAjuste", "INGRESO").toString();
            String comentario = payload.getOrDefault("comentario", "").toString();
            
            String motivoString = payload.getOrDefault("motivo", "INGRESO_NORMAL").toString();
            MotivoMerma motivo = MotivoMerma.valueOf(motivoString);

            // ⚡ ATRAPAMOS EL ID DE LA SUCURSAL QUE MANDÓ EL JAVASCRIPT
            Integer idSucursal = Integer.parseInt(payload.getOrDefault("idSucursal", "1").toString());

            // ⚡ SE LO PASAMOS AL SERVICIO (Añadimos idSucursal al final)
            boolean exito = inventarioService.ajustarStockRapido(codigo, cantidad, tipoAjuste, motivo, comentario, idSucursal);

            if (exito) {
                return ResponseEntity.ok().body("{\"mensaje\": \"Stock actualizado correctamente\"}");
            } else {
                return ResponseEntity.status(400).body("{\"error\": \"Error: Producto no encontrado o stock insuficiente para merma\"}");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("{\"error\": \"Motivo de merma no válido\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"error\": \"Error al procesar la solicitud\"}");
        }
    }
@GetMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<Map<String, Object>>> obtenerInventarioPorSucursal(@PathVariable Integer idSucursal) {
        
        // 1. Llamamos al servicio para traer SOLO el stock de este local
        List<Inventario> listaLocal = inventarioService.obtenerPorSucursal(idSucursal);
        
        // ¡Ojo aquí! Borramos el "if (listaLocal.isEmpty())"
        
        // 2. Mapeamos la información
        // Si listaLocal no tiene nada, Java simplemente creará una respuesta vacía [] automáticamente
        List<Map<String, Object>> respuesta = listaLocal.stream().map(inv -> {
            Map<String, Object> map = new HashMap<>();
            map.put("idProducto", inv.getIdProducto());
            map.put("stockActual", inv.getStockActual());
            map.put("idSucursal", idSucursal);
            
            // Buscamos los detalles visuales en el catálogo de productos
            productoRepository.findById(inv.getIdProducto()).ifPresent(p -> {
                map.put("nombreProducto", p.getNombre());
                map.put("precio", p.getPrecio());
                map.put("codigo", p.getCodigoBarras());
            });
            
            return map;
        }).collect(Collectors.toList());
        
        return ResponseEntity.ok(respuesta); // Devolverá [] al navegador, y no habrá error
    }
}