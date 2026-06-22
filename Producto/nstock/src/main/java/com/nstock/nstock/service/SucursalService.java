package com.nstock.nstock.service;


import com.nstock.nstock.entity.Sucursal;
import com.nstock.nstock.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// @Service le dice a Spring que aquí van las reglas de negocio de tu aplicación
@Service
public class SucursalService {

    // @Autowired "inyecta" el repositorio mágicamente para que podamos usarlo sin instanciarlo con "new"
    @Autowired
    private SucursalRepository sucursalRepository;

    // Método sencillo que va a la base de datos y trae todas las sucursales
    public List<Sucursal> obtenerTodasLasSucursales() {
        return sucursalRepository.findAll();
    }
}