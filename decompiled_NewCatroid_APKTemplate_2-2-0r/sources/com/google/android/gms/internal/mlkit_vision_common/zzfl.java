package com.google.android.gms.internal.mlkit_vision_common;

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes9.dex */
public enum zzfl implements zzv {
    SOURCE_UNKNOWN(0),
    BITMAP(1),
    BYTEARRAY(2),
    BYTEBUFFER(3),
    FILEPATH(4),
    ANDROID_MEDIA_IMAGE(5);

    private final int zzh;

    zzfl(int i) {
        this.zzh = i;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_common.zzv
    public final int zza() {
        return this.zzh;
    }
}
