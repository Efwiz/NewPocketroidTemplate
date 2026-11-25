package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import com.gaurav.avnc.vnc.XKeySym;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzno extends zznq {
    private final String zza;
    private final boolean zzb;
    private final int zzc;

    /* synthetic */ zzno(String str, boolean z, int i, zznn zznnVar) {
        this.zza = str;
        this.zzb = z;
        this.zzc = i;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof zznq) {
            zznq zznqVar = (zznq) obj;
            if (this.zza.equals(zznqVar.zzb()) && this.zzb == zznqVar.zzc() && this.zzc == zznqVar.zza()) {
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

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zznq
    public final int zza() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zznq
    public final String zzb() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zznq
    public final boolean zzc() {
        return this.zzb;
    }
}
