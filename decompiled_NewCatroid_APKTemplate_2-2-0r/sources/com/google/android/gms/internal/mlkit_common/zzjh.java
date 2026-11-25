package com.google.android.gms.internal.mlkit_common;

import com.gaurav.avnc.vnc.XKeySym;
import com.google.mlkit.common.sdkinternal.ModelType;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzjh extends zzjt {
    private final zzgy zza;
    private final String zzb;
    private final boolean zzc;
    private final boolean zzd;
    private final ModelType zze;
    private final zzhe zzf;
    private final int zzg;

    /* synthetic */ zzjh(zzgy zzgyVar, String str, boolean z, boolean z2, ModelType modelType, zzhe zzheVar, int i, zzjg zzjgVar) {
        this.zza = zzgyVar;
        this.zzb = str;
        this.zzc = z;
        this.zzd = z2;
        this.zze = modelType;
        this.zzf = zzheVar;
        this.zzg = i;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof zzjt) {
            zzjt zzjtVar = (zzjt) obj;
            if (this.zza.equals(zzjtVar.zzc()) && this.zzb.equals(zzjtVar.zze()) && this.zzc == zzjtVar.zzg() && this.zzd == zzjtVar.zzf() && this.zze.equals(zzjtVar.zzb()) && this.zzf.equals(zzjtVar.zzd()) && this.zzg == zzjtVar.zza()) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = (((this.zza.hashCode() ^ 1000003) * 1000003) ^ this.zzb.hashCode()) * 1000003;
        boolean z = this.zzc;
        int i = XKeySym.XK_kana_YU;
        int i2 = (iHashCode ^ (true != z ? 1237 : 1231)) * 1000003;
        if (true == this.zzd) {
            i = 1231;
        }
        return ((((((i2 ^ i) * 1000003) ^ this.zze.hashCode()) * 1000003) ^ this.zzf.hashCode()) * 1000003) ^ this.zzg;
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zza);
        String str = this.zzb;
        boolean z = this.zzc;
        boolean z2 = this.zzd;
        String strValueOf2 = String.valueOf(this.zze);
        String strValueOf3 = String.valueOf(this.zzf);
        int i = this.zzg;
        int length = String.valueOf(strValueOf).length();
        int length2 = str.length();
        StringBuilder sb = new StringBuilder(length + 187 + length2 + String.valueOf(strValueOf2).length() + String.valueOf(strValueOf3).length());
        sb.append("RemoteModelLoggingOptions{errorCode=");
        sb.append(strValueOf);
        sb.append(", tfliteSchemaVersion=");
        sb.append(str);
        sb.append(", shouldLogRoughDownloadTime=");
        sb.append(z);
        sb.append(", shouldLogExactDownloadTime=");
        sb.append(z2);
        sb.append(", modelType=");
        sb.append(strValueOf2);
        sb.append(", downloadStatus=");
        sb.append(strValueOf3);
        sb.append(", failureStatusCode=");
        sb.append(i);
        sb.append("}");
        return sb.toString();
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzjt
    public final int zza() {
        return this.zzg;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzjt
    public final ModelType zzb() {
        return this.zze;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzjt
    public final zzgy zzc() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzjt
    public final zzhe zzd() {
        return this.zzf;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzjt
    public final String zze() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzjt
    public final boolean zzf() {
        return this.zzd;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzjt
    public final boolean zzg() {
        return this.zzc;
    }
}
