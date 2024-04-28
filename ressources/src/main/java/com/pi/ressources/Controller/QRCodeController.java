package com.pi.ressources.Controller;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.pi.ressources.Services.QRCodeService;
import com.pi.ressources.Services.RessourceService;
import com.pi.ressources.entities.DecodedQrResponse;
import com.pi.ressources.entities.GenerateQrRequest;
import com.pi.ressources.entities.Ressource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Slf4j
@RestController
@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;

    @PostMapping(path = "/api/qr/generate", produces = MediaType.IMAGE_JPEG_VALUE)
    public void generateQr(@RequestBody Ressource urlRess, HttpServletResponse response) throws MissingServletRequestParameterException, WriterException, IOException {
        if (urlRess == null || urlRess.getUrlFile() == null || urlRess.getUrlFile().trim().equals("")) {
            throw new MissingServletRequestParameterException("qrString", "String");
        }
        String qrString = "http://192.168.43.124/Files/" + urlRess.getUrlFile();
        qrCodeService.generateQr(qrString, response.getOutputStream());
        response.getOutputStream().flush();
    }

    @PostMapping(path = "/api/qr/decode")
    public DecodedQrResponse decodeQr(@RequestParam("qrCode") MultipartFile qrCode) throws IOException, NotFoundException {
        String qrCodeString = qrCodeService.decodeQr(qrCode.getBytes());
        return new DecodedQrResponse(qrCodeString);
    }
}