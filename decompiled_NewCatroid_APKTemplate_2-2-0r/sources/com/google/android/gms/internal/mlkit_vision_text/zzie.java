package com.google.android.gms.internal.mlkit_vision_text;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public enum zzie implements zzda {
    UNKNOWN_FORMAT(0),
    NV16(1),
    NV21(2),
    YV12(3),
    YUV_420_888(7),
    JPEG(8),
    BITMAP(4),
    CM_SAMPLE_BUFFER_REF(5),
    UI_IMAGE(6);

    private final int zzj;

    zzie(int i) {
        this.zzj = i;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzda
    public final int zza() {
        return this.zzj;
    }
}
