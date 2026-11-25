package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbeq extends zzans<zzbeq, zzbep> implements zzapg {
    private static final zzbeq zzb;
    private zzaob<zzben> zze = zzI();

    static {
        zzbeq zzbeqVar = new zzbeq();
        zzb = zzbeqVar;
        zzans.zzM(zzbeq.class, zzbeqVar);
    }

    private zzbeq() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zze", zzben.class});
        }
        if (i2 == 3) {
            return new zzbeq();
        }
        zzbeo zzbeoVar = null;
        if (i2 == 4) {
            return new zzbep(zzbeoVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
