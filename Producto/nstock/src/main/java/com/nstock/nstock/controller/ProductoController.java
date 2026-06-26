package com.nstock.nstock.controller;

import com.nstock.nstock.entity.Producto;
import com.nstock.nstock.service.ProductoService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Endpoint: GET http://localhost:8081/api/productos/buscar?codigo=12345
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarProducto(@RequestParam String codigo) {
        try {
            Producto producto = productoService.buscarPorCodigo(codigo);
            return ResponseEntity.ok(producto);
        } catch (RuntimeException e) {
            // Si no existe, devolvemos un error 404 limpio
            return ResponseEntity.status(404).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

@PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody Map<String, Object> payload) {
        try {
            // RAYOS X: Para ver en la consola lo que llega del navegador
            System.out.println("--- INTENTO DE GUARDAR NUEVO PRODUCTO ---");
            System.out.println("Datos recibidos: " + payload);

            // 1. Armamos manualmente el objeto Producto
            Producto producto = new Producto();
            producto.setNombre(payload.get("nombre").toString());
            producto.setCodigoBarras(payload.get("codigoBarras").toString());
            producto.setPrecio(Integer.parseInt(payload.get("precio").toString()));
            producto.setCategoria(payload.getOrDefault("categoria", "General").toString());

            // 2. Atrapamos los nuevos datos de inventario
            Integer idSucursal = Integer.parseInt(payload.getOrDefault("idSucursal", "1").toString());
            Integer stockInicial = Integer.parseInt(payload.getOrDefault("stockInicial", "0").toString());

            // 3. Le pasamos todo al Service (Te marcará rojo hasta que hagamos el paso 2)
            Producto nuevoProducto = productoService.guardarProducto(producto, idSucursal, stockInicial);

            System.out.println("Resultado: ÉXITO. Producto guardado con ID: " + nuevoProducto.getIdProducto());
            
            return ResponseEntity.ok(nuevoProducto);

        } catch (Exception e) {
            System.out.println("Resultado: ERROR FATAL al intentar guardar:");
            e.printStackTrace();
            return ResponseEntity.status(500).body("{\"error\": \"Error al guardar en la base de datos\"}");
        }
    }

@PutMapping("/editar/{id}")
    public ResponseEntity<?> editarProducto(@PathVariable Integer id, @RequestBody Map<String, Object> payload) {
        try {
            // RAYOS X: Verificamos qué está llegando exactamente desde la pantalla
            System.out.println("--- INTENTO DE EDICIÓN ---");
            System.out.println("ID del Producto: " + id);
            System.out.println("Datos recibidos: " + payload);

            String nombre = payload.get("nombre").toString();
            Integer precio = Integer.parseInt(payload.get("precio").toString());

            boolean exito = productoService.editarNombreYPrecio(id, nombre, precio);

            if (exito) {
                System.out.println("Resultado: ÉXITO. Producto actualizado.");
                return ResponseEntity.ok().body("{\"mensaje\": \"Producto actualizado correctamente\"}");
            } else {
                System.out.println("Resultado: ERROR 404. El producto no existe en la base de datos.");
                return ResponseEntity.status(404).body("{\"error\": \"Producto no encontrado\"}");
            }
        } catch (Exception e) {
            // RAYOS X: Si algo explota, lo imprimimos en letras rojas
            System.out.println("Resultado: ERROR FATAL (Excepción):");
            e.printStackTrace(); 
            return ResponseEntity.status(500).body("{\"error\": \"Error al procesar la solicitud\"}");
        }
    }
    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> obtenerProductoPorId(@PathVariable Integer id) {
        try {
            Producto producto = productoService.buscarPorId(id);
            if (producto != null) {
                return ResponseEntity.ok(producto);
            }
            return ResponseEntity.status(404).body("{\"error\": \"Producto no encontrado\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"error\": \"Error interno\"}");
        }
    }
}

