package com.example.java_squad;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * Builds a bit matrix with message
 * returns the bitmap to image
 * Refrences: https://stackoverflow.com/questions/8800919/how-to-generate-a-qr-code-for-an-android-application,
 * https://developers.google.com/ml-kit/vision/barcode-scanning/android#java
 *
 * @author Kyle
 */

public class QRCode {
    QRCodeWriter writer = new QRCodeWriter();

    public Bitmap generate(String content) {
        BitMatrix bitMatrix = null;

        try {
            bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 513, 512);
        } catch (WriterException writerException) {
            writerException.printStackTrace();
        }

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }
}