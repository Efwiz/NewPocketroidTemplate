package com.google.mlkit.vision.mediapipe;

import android.graphics.Bitmap;
import java.nio.ByteBuffer;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes10.dex */
public class MediaPipeInputFactory {
    public static MediaPipeInput getMediaPipeInput(ByteBuffer byteBuffer, int i, int i2, long j) {
        return new zzd(byteBuffer, i, i2, j);
    }

    public static MediaPipeInput getMediaPipeInput(Bitmap bitmap, long j) {
        return new zze(bitmap, j);
    }

    private MediaPipeInputFactory() {
    }
}
