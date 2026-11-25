package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzga<K, V> {
    static <K, V> void zza(zzel zzelVar, zzfz<K, V> zzfzVar, K k, V v) throws IOException {
        zzeu.zza(zzelVar, zzfzVar.zza, 1, k);
        zzeu.zza(zzelVar, zzfzVar.zzc, 2, v);
    }

    static <K, V> int zza(zzfz<K, V> zzfzVar, K k, V v) {
        return zzeu.zza(zzfzVar.zza, 1, k) + zzeu.zza(zzfzVar.zzc, 2, v);
    }
}
