package com.nstock.nstock.controller;

import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin("*")
public class PagoController {

    @PostMapping("/crear")
    public ResponseEntity<?> crearPago() {

        try {

            PreferenceItemRequest item =
                    PreferenceItemRequest.builder()
                            .title("Venta NStock")
                            .quantity(1)
                            .unitPrice(new BigDecimal("15000"))
                            .build();

            PreferenceBackUrlsRequest backUrls =
                    PreferenceBackUrlsRequest.builder()
                            .success("https://www.google.com")
                            .pending("https://www.google.com")
                            .failure("https://www.google.com")
                            .build();

            PreferenceRequest preferenceRequest =
                    PreferenceRequest.builder()
                            .items(List.of(item))
                            .backUrls(backUrls)
                            .build();

            PreferenceClient client = new PreferenceClient();

            Preference preference = client.create(preferenceRequest);

            return ResponseEntity.ok(preference.getInitPoint());

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}