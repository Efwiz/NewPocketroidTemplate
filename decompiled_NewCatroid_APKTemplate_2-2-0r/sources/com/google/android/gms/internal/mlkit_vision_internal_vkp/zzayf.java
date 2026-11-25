package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzayf extends zzans<zzayf, zzaye> implements zzapg {
    private static final zzayf zzb;
    private int zze;
    private int zzf;
    private zzamk zzg = zzamk.zzb;
    private float zzh;

    static {
        zzayf zzayfVar = new zzayf();
        zzb = zzayfVar;
        zzans.zzM(zzayf.class, zzayfVar);
    }

    private zzayf() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001င\u0000\u0002ည\u0001\u0003ခ\u0002", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzayf();
        }
        zzaxx zzaxxVar = null;
        if (i2 == 4) {
            return new zzaye(zzaxxVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
