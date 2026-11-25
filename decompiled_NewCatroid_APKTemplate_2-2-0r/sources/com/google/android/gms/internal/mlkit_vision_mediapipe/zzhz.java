package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzhz {
    zzhz() {
    }

    abstract int zza(int i, byte[] bArr, int i2, int i3);

    abstract int zza(CharSequence charSequence, byte[] bArr, int i, int i2);

    abstract String zzb(byte[] bArr, int i, int i2) throws zzfi;

    final boolean zza(byte[] bArr, int i, int i2) {
        return zza(0, bArr, i, i2) == 0;
    }
}
