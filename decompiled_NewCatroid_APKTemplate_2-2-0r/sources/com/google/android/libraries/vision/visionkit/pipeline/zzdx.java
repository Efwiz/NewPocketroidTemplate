package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzdx extends zzans<zzdx, zzdw> implements zzapg {
    private static final zzdx zzb;
    private int zze;
    private zzcq zzf;
    private zzaw zzg;
    private zzaj zzh;
    private zzfa zzi;
    private boolean zzj;
    private zzaz zzk;
    private zzct zzl;
    private zzcl zzm;

    static {
        zzdx zzdxVar = new zzdx();
        zzb = zzdxVar;
        zzans.zzM(zzdx.class, zzdxVar);
    }

    private zzdx() {
    }

    public static zzdw zza() {
        return zzb.zzz();
    }

    static /* synthetic */ void zzd(zzdx zzdxVar, zzcq zzcqVar) {
        zzcqVar.getClass();
        zzdxVar.zzf = zzcqVar;
        zzdxVar.zze |= 1;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\b\u0000\u0001\u0001\t\b\u0000\u0000\u0000\u0001ဉ\u0001\u0003ဉ\u0005\u0004ဉ\u0000\u0005ဉ\u0002\u0006ဉ\u0003\u0007ဇ\u0004\bဉ\u0006\tဉ\u0007", new Object[]{"zze", "zzg", "zzk", "zzf", "zzh", "zzi", "zzj", "zzl", "zzm"});
        }
        if (i2 == 3) {
            return new zzdx();
        }
        zzdv zzdvVar = null;
        if (i2 == 4) {
            return new zzdw(zzdvVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
