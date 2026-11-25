package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzue extends zzans<zzue, zzud> implements zzapg {
    private static final zzue zzb;
    private int zze;
    private zzqv zzf;
    private zzqr zzg;
    private zzuh zzh;

    static {
        zzue zzueVar = new zzue();
        zzb = zzueVar;
        zzans.zzM(zzue.class, zzueVar);
    }

    private zzue() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzue();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zzud(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
