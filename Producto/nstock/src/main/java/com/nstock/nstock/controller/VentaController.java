package com.nstock.nstock.controller;

import com.nstock.nstock.dto.VentaRequestDTO;
import com.nstock.nstock.entity.Venta;
import com.nstock.nstock.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    // Endpoint: POST http://localhost:8081/api/ventas/registrar
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarVenta(@RequestBody VentaRequestDTO request) {
        try {
            Venta nuevaVenta = ventaService.registrarVenta(request);
            return ResponseEntity.ok(nuevaVenta);
        } catch (RuntimeException e) {
            // Si el servicio detecta falta de stock, devuelve un error controlado
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}