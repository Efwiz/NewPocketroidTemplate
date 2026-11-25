package com.google.android.gms.internal.mlkit_common;

import com.google.mlkit.common.sdkinternal.ModelType;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzjt {
    public static zzjs zzh() {
        zzjf zzjfVar = new zzjf();
        zzjfVar.zzg("NA");
        zzjfVar.zzf(false);
        zzjfVar.zze(false);
        zzjfVar.zzd(ModelType.UNKNOWN);
        zzjfVar.zzb(zzgy.NO_ERROR);
        zzjfVar.zza(zzhe.UNKNOWN_STATUS);
        zzjfVar.zzc(0);
        return zzjfVar;
    }

    public abstract int zza();

    public abstract ModelType zzb();

    public abstract zzgy zzc();

    public abstract zzhe zzd();

    public abstract String zze();

    public abstract boolean zzf();

    public abstract boolean zzg();
}
