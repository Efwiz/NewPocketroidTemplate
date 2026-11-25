package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public enum zzmm implements zzbz {
    UNKNOWN(0),
    CANONICAL(1),
    TFLITE(2),
    TFLITE_SUPPORT(3);

    private final int zzf;

    zzmm(int i) {
        this.zzf = i;
    }

    public static zzmm zzb(int i) {
        for (zzmm zzmmVar : values()) {
            if (zzmmVar.zzf == i) {
                return zzmmVar;
            }
        }
        return UNKNOWN;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzbz
    public final int zza() {
        return this.zzf;
    }
}
