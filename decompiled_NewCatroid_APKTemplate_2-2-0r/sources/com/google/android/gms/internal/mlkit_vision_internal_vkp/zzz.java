package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzz extends zzans<zzz, zzy> implements zzapg {
    private static final zzz zzb;
    private zzaob<zzac> zze = zzI();

    static {
        zzz zzzVar = new zzz();
        zzb = zzzVar;
        zzans.zzM(zzz.class, zzzVar);
    }

    private zzz() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zze", zzac.class});
        }
        if (i2 == 3) {
            return new zzz();
        }
        zzx zzxVar = null;
        if (i2 == 4) {
            return new zzy(zzxVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
