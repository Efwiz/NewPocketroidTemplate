package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzars extends zzans<zzars, zzarr> implements zzapg {
    private static final zzars zzb;
    private int zze;
    private zzasy zzf;
    private zzaob<zzatm> zzg = zzI();
    private zzaob<zzaro> zzh = zzI();

    static {
        zzars zzarsVar = new zzars();
        zzb = zzarsVar;
        zzans.zzM(zzars.class, zzarsVar);
    }

    private zzars() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0002\u0000\u0001ဉ\u0000\u0002\u001b\u0003\u001b", new Object[]{"zze", "zzf", "zzg", zzatm.class, "zzh", zzaro.class});
        }
        if (i2 == 3) {
            return new zzars();
        }
        zzarm zzarmVar = null;
        if (i2 == 4) {
            return new zzarr(zzarmVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
