package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzct extends zzans<zzct, zzcq> implements zzapg {
    private static final zzct zzb;
    private zzaob<zzcs> zze = zzI();

    static {
        zzct zzctVar = new zzct();
        zzb = zzctVar;
        zzans.zzM(zzct.class, zzctVar);
    }

    private zzct() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zze", zzcs.class});
        }
        if (i2 == 3) {
            return new zzct();
        }
        zzcp zzcpVar = null;
        if (i2 == 4) {
            return new zzcq(zzcpVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
