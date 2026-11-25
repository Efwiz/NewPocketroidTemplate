package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzxv extends zzans<zzxv, zzxt> implements zzapg {
    private static final zzxv zzb;
    private int zze;
    private zzyc zzf;
    private float zzg;
    private int zzh;
    private boolean zzi;

    static {
        zzxv zzxvVar = new zzxv();
        zzb = zzxvVar;
        zzans.zzM(zzxv.class, zzxvVar);
    }

    private zzxv() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဉ\u0000\u0002ခ\u0001\u0003ဌ\u0002\u0004ဇ\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", zzxu.zza, "zzi"});
        }
        if (i2 == 3) {
            return new zzxv();
        }
        zzxs zzxsVar = null;
        if (i2 == 4) {
            return new zzxt(zzxsVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
