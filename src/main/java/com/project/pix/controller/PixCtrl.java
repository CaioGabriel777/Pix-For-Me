package com.project.pix.controller;

import com.project.pix.PixPayloadGenerator;
import com.project.pix.QRCodeGenerator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/pix")
public class PixCtrl {

    private static final String minhaChavePix = "e3f48c1f-db06-42c9-a5d1-ea508f62218a";

    @GetMapping("/qrcode")
    public ResponseEntity<byte[]> gerarQrcode(@RequestParam BigDecimal valor) throws Exception {

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String payload = PixPayloadGenerator.gerarPayload(minhaChavePix, valor, "CAIO GABRIEL", "SALVADOR");

        byte[] qrcode = QRCodeGenerator.gerarQRCode(payload, 400, 400);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(qrcode, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/copia-e-cola", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> gerarCopiaECola(@RequestParam("valor") BigDecimal valor) {

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String payload = PixPayloadGenerator.gerarPayload(minhaChavePix, valor, "CAIO GABRIEL", "SALVADOR");

        return ResponseEntity.ok(payload);
    }
}
