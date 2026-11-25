package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzya extends zzans<zzya, zzxz> implements zzapg {
    private static final zzya zzb;
    private int zze;
    private float zzf;
    private float zzg;

    static {
        zzya zzyaVar = new zzya();
        zzb = zzyaVar;
        zzans.zzM(zzya.class, zzyaVar);
    }

    private zzya() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ခ\u0000\u0002ခ\u0001", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzya();
        }
        zzxx zzxxVar = null;
        if (i2 == 4) {
            return new zzxz(zzxxVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
