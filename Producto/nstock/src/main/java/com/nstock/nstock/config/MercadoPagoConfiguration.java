package com.nstock.nstock.config;

import com.mercadopago.MercadoPagoConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MercadoPagoConfiguration {

    // PEGA AQUÍ TU ACCESS TOKEN TEST
    private final String ACCESS_TOKEN = "APP_USR-912580725662166-051509-4c1c29966c67c0950741d088a6d55d7f-3404523546";

    @PostConstruct
    public void init() {
        MercadoPagoConfig.setAccessToken(ACCESS_TOKEN);
    }
}