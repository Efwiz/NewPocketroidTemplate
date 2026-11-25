package com.google.android.gms.internal.mlkit_common;

import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzjc extends zzjj {
    private String zza;
    private Boolean zzb;
    private Integer zzc;

    zzjc() {
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzjj
    public final zzjj zza(boolean z) {
        this.zzb = true;
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzjj
    public final zzjj zzb(int i) {
        this.zzc = 1;
        return this;
    }

    public final zzjj zzc(String str) {
        this.zza = NativeLibraryConstantsKt.KONAN_DISTRIBUTION_COMMON_LIBS_DIR;
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzjj
    public final zzjk zzd() {
        Boolean bool;
        String str = this.zza;
        if (str != null && (bool = this.zzb) != null && this.zzc != null) {
            return new zzje(str, bool.booleanValue(), this.zzc.intValue(), null);
        }
        StringBuilder sb = new StringBuilder();
        if (this.zza == null) {
            sb.append(" libraryName");
        }
        if (this.zzb == null) {
            sb.append(" enableFirelog");
        }
        if (this.zzc == null) {
            sb.append(" firelogEventType");
        }
        String strValueOf = String.valueOf(sb);
        StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf).length() + 28);
        sb2.append("Missing required properties:");
        sb2.append(strValueOf);
        throw new IllegalStateException(sb2.toString());
    }
}
