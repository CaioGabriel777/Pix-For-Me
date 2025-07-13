package com.project.pix;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.UUID;

public class PixPayloadGenerator {

    private static String formatarCampo(String id, String valor) {
        String tamanho = String.format("%02d", valor.length());
        return id + tamanho + valor;
    }

    public static String gerarPayload(String chavePix, BigDecimal valor, String nome, String cidade) {
        String payloadFormatIndicator = "000201";
        String merchantCategoryCode = "52040000";
        String transactionCurrency = "5303986";
        String countryCode = "5802BR";
        String crc16Id = "6304";

        String valorFormatado = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US)).format(valor);
        String nomeFormatado = nome.length() > 25 ? nome.substring(0, 25) : nome;
        String cidadeFormatada = cidade.length() > 15 ? cidade.substring(0, 15) : cidade;
        String txid = UUID.randomUUID().toString().replace("-", "").substring(0, 25);


        String gui = formatarCampo("00", "br.gov.bcb.pix");
        String chave = formatarCampo("01", chavePix);
        String merchantAccountInfo = formatarCampo("26", gui + chave);

        String transactionAmount = formatarCampo("54", valorFormatado);

        String merchantName = formatarCampo("59", nomeFormatado);

        String merchantCity = formatarCampo("60", cidadeFormatada);

        String txidField = formatarCampo("05", txid);
        String additionalData = formatarCampo("62", txidField);

        StringBuilder payload = new StringBuilder();
        payload.append(payloadFormatIndicator)
                .append(merchantAccountInfo)
                .append(merchantCategoryCode)
                .append(transactionCurrency)
                .append(transactionAmount)
                .append(countryCode)
                .append(merchantName)
                .append(merchantCity)
                .append(additionalData);

        payload.append(crc16Id);
        String crc16 = calcularCRC16(payload.toString());
        payload.append(crc16);

        return payload.toString();
    }

    private static String calcularCRC16(String payload) {
        int crc = 0xFFFF;
        int polynomial = 0x1021;
        byte[] bytes = payload.getBytes();
        for (byte b : bytes) {
            crc ^= ((int) b & 0xFF) << 8;
            for (int i = 0; i < 8; i++) {
                if ((crc & 0x8000) != 0) {
                    crc = (crc << 1) ^ polynomial;
                } else {
                    crc <<= 1;
                }
            }
        }
        return String.format("%04X", crc & 0xFFFF);
    }
}