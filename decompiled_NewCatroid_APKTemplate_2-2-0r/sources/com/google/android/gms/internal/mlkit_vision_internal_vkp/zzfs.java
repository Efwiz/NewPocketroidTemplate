package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzfs extends zzans<zzfs, zzfr> implements zzapg {
    private static final zzfs zzb;
    private int zze;
    private float zzf = 0.7f;
    private int zzg = 2;
    private float zzh = 0.2f;

    static {
        zzfs zzfsVar = new zzfs();
        zzb = zzfsVar;
        zzans.zzM(zzfs.class, zzfsVar);
    }

    private zzfs() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ခ\u0000\u0002င\u0001\u0003ခ\u0002", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzfs();
        }
        zzfq zzfqVar = null;
        if (i2 == 4) {
            return new zzfr(zzfqVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
