package com.project.pix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;

public class QRCodeGenerator {

    public static byte[] gerarQRCode(String texto, int largura, int altura) throws Exception {
        BitMatrix matrix = new MultiFormatWriter().encode(texto, BarcodeFormat.QR_CODE, largura, altura);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", out);
        return out.toByteArray();
    }

}
