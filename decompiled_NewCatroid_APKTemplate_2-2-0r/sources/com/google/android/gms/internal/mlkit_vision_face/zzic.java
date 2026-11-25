package com.google.android.gms.internal.mlkit_vision_face;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public enum zzic implements zzcu {
    UNKNOWN_PERFORMANCE(0),
    FAST(1),
    ACCURATE(2);

    private final int zzd;

    zzic(int i) {
        this.zzd = i;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzcu
    public final int zza() {
        return this.zzd;
    }
}
