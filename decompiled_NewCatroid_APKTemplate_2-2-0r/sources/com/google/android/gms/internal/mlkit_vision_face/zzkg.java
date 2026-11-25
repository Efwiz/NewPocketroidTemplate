package com.google.android.gms.internal.mlkit_vision_face;

import com.gaurav.avnc.vnc.XKeySym;
import com.google.android.datatransport.Priority;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
final class zzkg extends zzki {
    private final String zza;
    private final boolean zzb;
    private final boolean zzc;
    private final Priority zzd;
    private final int zze;

    /* synthetic */ zzkg(String str, boolean z, boolean z2, Priority priority, int i, zzke zzkeVar) {
        this.zza = str;
        this.zzb = z;
        this.zzc = z2;
        this.zzd = priority;
        this.zze = i;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof zzki) {
            zzki zzkiVar = (zzki) obj;
            if (this.zza.equals(zzkiVar.zza()) && this.zzb == zzkiVar.zzb() && this.zzc == zzkiVar.zzc() && this.zzd.equals(zzkiVar.zzd()) && this.zze == zzkiVar.zze()) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = (this.zza.hashCode() ^ 1000003) * 1000003;
        boolean z = this.zzb;
        int i = XKeySym.XK_kana_YU;
        int i2 = (iHashCode ^ (true != z ? 1237 : 1231)) * 1000003;
        if (true == this.zzc) {
            i = 1231;
        }
        return ((((i2 ^ i) * 1000003) ^ this.zzd.hashCode()) * 1000003) ^ this.zze;
    }

    public final String toString() {
        String str = this.zza;
        boolean z = this.zzb;
        boolean z2 = this.zzc;
        String strValueOf = String.valueOf(this.zzd);
        int i = this.zze;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 129 + String.valueOf(strValueOf).length());
        sb.append("MLKitLoggingOptions{libraryName=");
        sb.append(str);
        sb.append(", enableClearcut=");
        sb.append(z);
        sb.append(", enableFirelog=");
        sb.append(z2);
        sb.append(", firelogEventPriority=");
        sb.append(strValueOf);
        sb.append(", firelogEventType=");
        sb.append(i);
        sb.append("}");
        return sb.toString();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzki
    public final String zza() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzki
    public final boolean zzb() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzki
    public final boolean zzc() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzki
    public final Priority zzd() {
        return this.zzd;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzki
    public final int zze() {
        return this.zze;
    }
}
