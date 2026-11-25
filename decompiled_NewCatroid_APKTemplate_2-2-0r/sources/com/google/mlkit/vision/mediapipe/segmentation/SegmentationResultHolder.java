package com.google.mlkit.vision.mediapipe.segmentation;

import java.nio.ByteBuffer;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes10.dex */
public class SegmentationResultHolder {
    private final ByteBuffer zza;
    private final int zzb;
    private final int zzc;

    public SegmentationResultHolder(ByteBuffer byteBuffer, int i, int i2) {
        this.zza = byteBuffer;
        this.zzb = i;
        this.zzc = i2;
    }

    public ByteBuffer getMask() {
        return this.zza;
    }

    public int getWidth() {
        return this.zzb;
    }

    public int getHeight() {
        return this.zzc;
    }
}
