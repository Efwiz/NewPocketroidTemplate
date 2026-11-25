package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzeo extends zzans<zzeo, zzen> implements zzapg {
    private static final zzeo zzb;
    private int zze;
    private zzamk zzf = zzamk.zzb;
    private float zzg;

    static {
        zzeo zzeoVar = new zzeo();
        zzb = zzeoVar;
        zzans.zzM(zzeo.class, zzeoVar);
    }

    private zzeo() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ည\u0000\u0002ခ\u0001", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzeo();
        }
        zzem zzemVar = null;
        if (i2 == 4) {
            return new zzen(zzemVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
