package com.google.android.gms.internal.mlkit_common;

import com.google.mlkit.common.sdkinternal.ModelType;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzjf extends zzjs {
    private zzgy zza;
    private String zzb;
    private Boolean zzc;
    private Boolean zzd;
    private ModelType zze;
    private zzhe zzf;
    private Integer zzg;

    zzjf() {
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzjs
    public final zzjs zza(zzhe zzheVar) {
        if (zzheVar == null) {
            throw new NullPointerException("Null downloadStatus");
        }
        this.zzf = zzheVar;
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzjs
    public final zzjs zzb(zzgy zzgyVar) {
        if (zzgyVar == null) {
            throw new NullPointerException("Null errorCode");
        }
        this.zza = zzgyVar;
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzjs
    public final zzjs zzc(int i) {
        this.zzg = Integer.valueOf(i);
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzjs
    public final zzjs zzd(ModelType modelType) {
        if (modelType == null) {
            throw new NullPointerException("Null modelType");
        }
        this.zze = modelType;
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzjs
    public final zzjs zze(boolean z) {
        this.zzd = Boolean.valueOf(z);
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzjs
    public final zzjs zzf(boolean z) {
        this.zzc = Boolean.valueOf(z);
        return this;
    }

    public final zzjs zzg(String str) {
        this.zzb = "NA";
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzjs
    public final zzjt zzh() {
        String str;
        Boolean bool;
        zzgy zzgyVar = this.zza;
        if (zzgyVar != null && (str = this.zzb) != null && (bool = this.zzc) != null && this.zzd != null && this.zze != null && this.zzf != null && this.zzg != null) {
            return new zzjh(zzgyVar, str, bool.booleanValue(), this.zzd.booleanValue(), this.zze, this.zzf, this.zzg.intValue(), null);
        }
        StringBuilder sb = new StringBuilder();
        if (this.zza == null) {
            sb.append(" errorCode");
        }
        if (this.zzb == null) {
            sb.append(" tfliteSchemaVersion");
        }
        if (this.zzc == null) {
            sb.append(" shouldLogRoughDownloadTime");
        }
        if (this.zzd == null) {
            sb.append(" shouldLogExactDownloadTime");
        }
        if (this.zze == null) {
            sb.append(" modelType");
        }
        if (this.zzf == null) {
            sb.append(" downloadStatus");
        }
        if (this.zzg == null) {
            sb.append(" failureStatusCode");
        }
        String strValueOf = String.valueOf(sb);
        StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf).length() + 28);
        sb2.append("Missing required properties:");
        sb2.append(strValueOf);
        throw new IllegalStateException(sb2.toString());
    }
}
