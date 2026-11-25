package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaxs extends zzans<zzaxs, zzaxp> implements zzapg {
    private static final zzaxs zzb;
    private int zze;
    private float zzf;
    private float zzg;
    private float zzh;
    private int zzi = 15000;
    private int zzj;
    private float zzk;

    static {
        zzaxs zzaxsVar = new zzaxs();
        zzb = zzaxsVar;
        zzans.zzM(zzaxs.class, zzaxsVar);
    }

    private zzaxs() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001ခ\u0000\u0002ခ\u0001\u0003ခ\u0002\u0004ဌ\u0003\u0005ဌ\u0004\u0006ခ\u0005", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", zzaxq.zza, "zzj", zzaxr.zza, "zzk"});
        }
        if (i2 == 3) {
            return new zzaxs();
        }
        zzaxg zzaxgVar = null;
        if (i2 == 4) {
            return new zzaxp(zzaxgVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
