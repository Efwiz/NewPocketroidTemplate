package com.google.android.gms.internal.mlkit_common;

import com.gaurav.avnc.vnc.XKeySym;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzje extends zzjk {
    private final String zza;
    private final boolean zzb;
    private final int zzc;

    /* synthetic */ zzje(String str, boolean z, int i, zzjd zzjdVar) {
        this.zza = str;
        this.zzb = z;
        this.zzc = i;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof zzjk) {
            zzjk zzjkVar = (zzjk) obj;
            if (this.zza.equals(zzjkVar.zzb()) && this.zzb == zzjkVar.zzc() && this.zzc == zzjkVar.zza()) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return ((((this.zza.hashCode() ^ 1000003) * 1000003) ^ (true != this.zzb ? XKeySym.XK_kana_YU : XKeySym.XK_kana_MA)) * 1000003) ^ this.zzc;
    }

    public final String toString() {
        String str = this.zza;
        boolean z = this.zzb;
        int i = this.zzc;
        StringBuilder sb = new StringBuilder(str.length() + 84);
        sb.append("MLKitLoggingOptions{libraryName=");
        sb.append(str);
        sb.append(", enableFirelog=");
        sb.append(z);
        sb.append(", firelogEventType=");
        sb.append(i);
        sb.append("}");
        return sb.toString();
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzjk
    public final int zza() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzjk
    public final String zzb() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzjk
    public final boolean zzc() {
        return this.zzb;
    }
}
