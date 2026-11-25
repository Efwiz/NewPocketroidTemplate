package com.google.mlkit.vision.mediapipe.utils;

import com.google.mlkit.common.MlKitException;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.common.internal.ImageConvertUtils;
import java.nio.ByteBuffer;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes10.dex */
public class ImageConvertNativeUtils {
    private ImageConvertNativeUtils() {
    }

    private static native byte[] byteArrayToRgb(byte[] bArr, int i, int i2, int i3, int i4);

    private static native byte[] yuvPlanesToRgb(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, ByteBuffer byteBuffer3, int i, int i2, int i3, int i4, int i5, int i6);

    public static byte[] getRgbBuffer(InputImage inputImage) throws MlKitException {
        if (inputImage.getByteBuffer() != null && (inputImage.getFormat() == 17 || inputImage.getFormat() == 842094169)) {
            return byteArrayToRgb(ImageConvertUtils.getInstance().byteBufferToByteArray(inputImage.getByteBuffer()), inputImage.getWidth(), inputImage.getHeight(), inputImage.getRotationDegrees(), inputImage.getFormat());
        }
        if (inputImage.getFormat() == 35 && inputImage.getPlanes() != null && inputImage.getPlanes().length == 3) {
            return yuvPlanesToRgb(inputImage.getPlanes()[0].getBuffer(), inputImage.getPlanes()[1].getBuffer(), inputImage.getPlanes()[2].getBuffer(), inputImage.getWidth(), inputImage.getHeight(), inputImage.getPlanes()[0].getRowStride(), inputImage.getPlanes()[1].getRowStride(), inputImage.getPlanes()[1].getPixelStride(), inputImage.getRotationDegrees());
        }
        return null;
    }
}
