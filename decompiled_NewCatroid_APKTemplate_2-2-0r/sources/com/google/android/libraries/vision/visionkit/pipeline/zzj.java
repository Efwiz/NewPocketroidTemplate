package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzj extends zzans<zzj, zzi> implements zzapg {
    private static final zzj zzb;
    private int zze;
    private int zzf;
    private zzde zzg;

    static {
        zzj zzjVar = new zzj();
        zzb = zzjVar;
        zzans.zzM(zzj.class, zzjVar);
    }

    private zzj() {
    }

    public static zzi zza() {
        return zzb.zzz();
    }

    static /* synthetic */ void zzd(zzj zzjVar, int i) {
        zzjVar.zzf = 1;
        zzjVar.zze = 1 | zzjVar.zze;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဉ\u0001", new Object[]{"zze", "zzf", zzn.zza, "zzg"});
        }
        if (i2 == 3) {
            return new zzj();
        }
        zzh zzhVar = null;
        if (i2 == 4) {
            return new zzi(zzhVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
