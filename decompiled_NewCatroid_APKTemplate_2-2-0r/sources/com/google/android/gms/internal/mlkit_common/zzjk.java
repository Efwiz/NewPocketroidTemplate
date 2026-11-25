package com.google.android.gms.internal.mlkit_common;

import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzjk {
    public static zzjj zzd(String str) {
        zzjc zzjcVar = new zzjc();
        zzjcVar.zzc(NativeLibraryConstantsKt.KONAN_DISTRIBUTION_COMMON_LIBS_DIR);
        zzjcVar.zza(true);
        zzjcVar.zzb(1);
        return zzjcVar;
    }

    public abstract int zza();

    public abstract String zzb();

    public abstract boolean zzc();
}
