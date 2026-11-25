package com.google.mlkit.vision.common.internal;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.SystemClock;
import com.google.android.gms.internal.mlkit_vision_common.zzig;
import com.google.android.gms.internal.mlkit_vision_common.zzii;
import com.google.android.odml.image.BitmapExtractor;
import com.google.android.odml.image.ByteBufferExtractor;
import com.google.android.odml.image.ImageProperties;
import com.google.android.odml.image.MediaImageExtractor;
import com.google.android.odml.image.MlImage;
import com.google.mlkit.vision.common.InputImage;
import java.nio.ByteBuffer;

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes10.dex */
public class CommonConvertUtils {
    public static int convertToAndroidImageFormat(int i) {
        int i2 = 17;
        if (i != 17) {
            i2 = 35;
            if (i != 35) {
                i2 = 842094169;
                if (i != 842094169) {
                    return 0;
                }
            }
        }
        return i2;
    }

    public static int convertToMVRotation(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 90) {
            return 1;
        }
        if (i == 180) {
            return 2;
        }
        if (i == 270) {
            return 3;
        }
        StringBuilder sb = new StringBuilder(29);
        sb.append("Invalid rotation: ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    static InputImage zza(MlImage mlImage) {
        Integer num;
        ImageProperties imageProperties = mlImage.getContainedImageProperties().get(0);
        int storageType = imageProperties.getStorageType();
        if (storageType == 1) {
            Bitmap bitmapExtract = BitmapExtractor.extract(mlImage);
            zzb(-1, 1, SystemClock.elapsedRealtime(), mlImage.getHeight(), mlImage.getWidth(), bitmapExtract.getAllocationByteCount(), mlImage.getRotation());
            return InputImage.fromBitmap(bitmapExtract, mlImage.getRotation());
        }
        if (storageType != 2) {
            if (storageType != 3) {
                return null;
            }
            Image imageExtract = MediaImageExtractor.extract(mlImage);
            zzb(imageExtract.getFormat(), 5, SystemClock.elapsedRealtime(), mlImage.getHeight(), mlImage.getWidth(), imageExtract.getFormat() == 256 ? imageExtract.getPlanes()[0].getBuffer().limit() : (imageExtract.getPlanes()[0].getBuffer().limit() * 3) / 2, mlImage.getRotation());
            return InputImage.fromMediaImage(imageExtract, mlImage.getRotation());
        }
        ByteBuffer byteBufferExtract = ByteBufferExtractor.extract(mlImage);
        int imageFormat = imageProperties.getImageFormat();
        if (imageFormat != 4) {
            num = imageFormat != 5 ? null : 842094169;
        } else {
            num = 17;
        }
        if (num == null) {
            return null;
        }
        zzb(num.intValue(), 3, SystemClock.elapsedRealtime(), mlImage.getHeight(), mlImage.getWidth(), byteBufferExtract.limit(), mlImage.getRotation());
        return InputImage.fromByteBuffer(byteBufferExtract, mlImage.getWidth(), mlImage.getHeight(), mlImage.getRotation(), num.intValue());
    }

    private static void zzb(int i, int i2, long j, int i3, int i4, int i5, int i6) {
        zzii.zzb(zzig.zzb("vision-common"), i, i2, j, i3, i4, i5, i6);
    }
}
