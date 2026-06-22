package com.nstock.nstock.controller;

import com.nstock.nstock.entity.Sucursal;
import com.nstock.nstock.service.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// @RestController indica que esta clase responderá datos a través de internet (en formato JSON)
@RestController
@CrossOrigin("*")
// @RequestMapping define la URL principal para entrar a este módulo
@RequestMapping("/api/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    // @GetMapping indica que si alguien entra a esta URL desde su navegador, se ejecutará este método
    @GetMapping
    public List<Sucursal> listar() {
        return sucursalService.obtenerTodasLasSucursales();
    }
}