package com.google.android.gms.internal.mlkit_vision_face;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public enum zzhz implements zzcu {
    UNKNOWN_CLASSIFICATIONS(0),
    NO_CLASSIFICATIONS(1),
    ALL_CLASSIFICATIONS(2);

    private final int zzd;

    zzhz(int i) {
        this.zzd = i;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzcu
    public final int zza() {
        return this.zzd;
    }
}
