package com.google.android.gms.internal.mlkit_vision_face;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public enum zzia implements zzcu {
    UNKNOWN_CONTOURS(0),
    NO_CONTOURS(1),
    ALL_CONTOURS(2);

    private final int zzd;

    zzia(int i) {
        this.zzd = i;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzcu
    public final int zza() {
        return this.zzd;
    }
}
