package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbbm extends zzans<zzbbm, zzbbl> implements zzapg {
    private static final zzbbm zzb;
    private int zze;
    private float zzf;
    private zzamk zzg = zzamk.zzb;

    static {
        zzbbm zzbbmVar = new zzbbm();
        zzb = zzbbmVar;
        zzans.zzM(zzbbm.class, zzbbmVar);
    }

    private zzbbm() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ခ\u0000\u0002ည\u0001", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzbbm();
        }
        zzbbk zzbbkVar = null;
        if (i2 == 4) {
            return new zzbbl(zzbbkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
