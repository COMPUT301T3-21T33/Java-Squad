package com.example.java_squad;

public interface QrCodeListener {
    void onQrCodeFound(String Qrcode);
    void qrCodeNotFound();
}
