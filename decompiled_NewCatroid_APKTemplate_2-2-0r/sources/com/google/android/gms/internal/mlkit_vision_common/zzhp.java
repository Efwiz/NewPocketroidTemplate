package com.google.android.gms.internal.mlkit_vision_common;

import com.gaurav.avnc.vnc.XKeySym;

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzhp extends zzhr {
    private final String zza;
    private final boolean zzb;
    private final int zzc;

    /* synthetic */ zzhp(String str, boolean z, int i, zzho zzhoVar) {
        this.zza = str;
        this.zzb = z;
        this.zzc = i;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof zzhr) {
            zzhr zzhrVar = (zzhr) obj;
            if (this.zza.equals(zzhrVar.zzb()) && this.zzb == zzhrVar.zzc() && this.zzc == zzhrVar.zza()) {
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

    @Override // com.google.android.gms.internal.mlkit_vision_common.zzhr
    public final int zza() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_common.zzhr
    public final String zzb() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_common.zzhr
    public final boolean zzc() {
        return this.zzb;
    }
}
