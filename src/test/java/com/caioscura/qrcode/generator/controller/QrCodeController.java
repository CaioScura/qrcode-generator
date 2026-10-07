package com.caioscura.qrcode.generator.controller;


import com.caioscura.qrcode.generator.dto.qrcode.QrCodeGenerateRequest;
import com.caioscura.qrcode.generator.dto.qrcode.QrCodeGenerateResponse;
import com.caioscura.qrcode.generator.service.QrCodeGeneratorService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qrcode")
public class QrCodeController {

    private final QrCodeGeneratorService qrCodeGeneratorService;


    //construtor que injeta a dependencia do service no controller
    public QrCodeController(QrCodeGeneratorService qrCodeGeneratorService) {
        this.qrCodeGeneratorService = qrCodeGeneratorService;
    }


    //endpoint que recebe a requisicao do usuario e chama o service para gerar o qr code
    @PostMapping
    public ResponseEntity<QrCodeGenerateResponse> generate(@RequestBody QrCodeGenerateRequest request){
        try{
            QrCodeGenerateResponse response = this.qrCodeGeneratorService.generateAndUploadQrCode(request.text());
            return ResponseEntity.ok(response);

        }catch (Exception e){
//            System.out.println(e);
            return ResponseEntity.internalServerError().build();
        }

    }
}
