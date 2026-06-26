package com.nstock.nstock.entity;

public enum MotivoMerma {

    INGRESO_NORMAL,      // Para cuando agregas stock (no requiere justificación)
    VENCIMIENTO,         // Producto caducado
    DANO_EMPAQUE,        // Roto, abollado, mojado
    ROBO_PERDIDA,        // Extravío de mercadería
    CONSUMO_INTERNO,     // Uso para el local
    ERROR_SISTEMA        // Cuadratura de inventario
}
