package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public enum zzlt implements zzbz {
    MODE_UNSPECIFIED(0),
    STREAM(1),
    SINGLE_IMAGE(2);

    private final int zze;

    zzlt(int i) {
        this.zze = i;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzbz
    public final int zza() {
        return this.zze;
    }
}
