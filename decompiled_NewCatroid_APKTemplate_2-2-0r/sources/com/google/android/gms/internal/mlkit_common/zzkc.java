package com.google.android.gms.internal.mlkit_common;

import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzkc {
    private static zzkb zza;

    public static synchronized zzjr zza(zzjk zzjkVar) {
        if (zza == null) {
            zza = new zzkb(null);
        }
        return zza.get(zzjkVar);
    }

    public static synchronized zzjr zzb(String str) {
        return zza(zzjk.zzd(NativeLibraryConstantsKt.KONAN_DISTRIBUTION_COMMON_LIBS_DIR).zzd());
    }
}
