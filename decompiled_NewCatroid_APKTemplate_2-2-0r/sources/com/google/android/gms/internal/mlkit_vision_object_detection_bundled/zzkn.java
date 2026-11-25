package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public enum zzkn implements zzbz {
    TYPE_UNKNOWN(0),
    TYPE_THIN(1),
    TYPE_THICK(2);

    private final int zze;

    zzkn(int i) {
        this.zze = i;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzbz
    public final int zza() {
        return this.zze;
    }
}
