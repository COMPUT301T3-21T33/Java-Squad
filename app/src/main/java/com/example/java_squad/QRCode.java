package com.example.java_squad;

import android.graphics.Bitmap;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;
import com.google.zxing.qrcode.QRCodeWriter;

import java.nio.ByteBuffer;

import static android.graphics.ImageFormat.YUV_420_888;
import static android.graphics.ImageFormat.YUV_422_888;
import static android.graphics.ImageFormat.YUV_444_888;

/**
 * Builds a bit matrix with message
 * returns the bitmap to image
 * Refrences: https://stackoverflow.com/questions/8800919/how-to-generate-a-qr-code-for-an-android-application,
 *
 * @author Kyle
 */

public class QRCode implements ImageAnalysis.Analyzer {
    private QrCodeListener listener;
    QRCodeWriter writer = new QRCodeWriter();
    public QRCode(QrCodeListener listener) {
        this.listener = listener;
    }

    public QRCode() {

    }

    public Bitmap Bitmap(QrCodeTrial content){
        BitMatrix bitMatrix = null;

        // use try-catch block to encode a bit matrix
        try {
            bitMatrix = writer.encode(String.valueOf(content), BarcodeFormat.QR_CODE, 512, 512);
        } catch (WriterException writerException) {
            writerException.printStackTrace();
        }

        // set up bitmatrix
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

    @Override
    public void analyze(@NonNull ImageProxy Bitmap) {
        if (Bitmap.getFormat() == YUV_420_888 || Bitmap.getFormat() == YUV_422_888 || Bitmap.getFormat() == YUV_444_888) {
            ByteBuffer byteBuffer = Bitmap.getPlanes()[0].getBuffer();
            byte[] imageData = new byte[byteBuffer.capacity()];
            byteBuffer.get(imageData);

            PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(
                    imageData,
                    Bitmap.getWidth(), Bitmap.getHeight(),
                    0, 0,
                    Bitmap.getWidth(), Bitmap.getHeight(),
                    false
            );

            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

            try {
                Result result = new QRCodeMultiReader().decode(binaryBitmap);
                listener.onQrCodeFound(result.getText());
            } catch (FormatException | ChecksumException | NotFoundException e) {
                listener.qrCodeNotFound();
            }
        }

        Bitmap.close();
    }
}