package com.pi.ressources.Services;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.io.OutputStream;

public interface QRCodeService {

    void generateQr(String data, OutputStream outputStream) throws WriterException, IOException;

    String decodeQr(byte[] data) throws IOException, NotFoundException;
}
