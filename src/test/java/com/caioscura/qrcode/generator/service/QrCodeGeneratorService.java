package com.caioscura.qrcode.generator.service;

import com.caioscura.qrcode.generator.dto.qrcode.QrCodeGenerateResponse;
import com.caioscura.qrcode.generator.ports.StoragePort;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class QrCodeGeneratorService {
    private final StoragePort storage;


    public QrCodeGeneratorService(StoragePort storage) {
        this.storage = storage;
    }


    //funcao que gera o qr code e envia para o storage, retornando a url do arquivo
    public QrCodeGenerateResponse generateAndUploadQrCode(String text) throws WriterException, IOException {
        //vem da biblioteca zxing do google
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

        //serve para converter a imagem em bytes e enviar para o storage
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

        byte[] pngQrCodeData = pngOutputStream.toByteArray();

        String url = storage.uploadFile(pngQrCodeData, UUID.randomUUID().toString(), "image/png");
        return new QrCodeGenerateResponse(url);
    }
}
