package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzavi extends zzans<zzavi, zzavh> implements zzapg {
    private static final zzavi zzb;
    private int zze;
    private int zzf;
    private int zzg;
    private zzaob<zzavf> zzh = zzI();

    static {
        zzavi zzaviVar = new zzavi();
        zzb = zzaviVar;
        zzans.zzM(zzavi.class, zzaviVar);
    }

    private zzavi() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0001\u0000\u0001င\u0000\u0002င\u0001\u0003\u001b", new Object[]{"zze", "zzf", "zzg", "zzh", zzavf.class});
        }
        if (i2 == 3) {
            return new zzavi();
        }
        zzatr zzatrVar = null;
        if (i2 == 4) {
            return new zzavh(zzatrVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
