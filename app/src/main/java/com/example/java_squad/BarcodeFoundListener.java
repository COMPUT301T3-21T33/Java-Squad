package com.example.java_squad;

public interface BarcodeFoundListener {
    void onBarcodeFound(String barcode);
    void barcodeNotFound();
}
