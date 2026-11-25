package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbdy extends zzans<zzbdy, zzbdx> implements zzapg {
    private static final zzbdy zzb;
    private zzaob<zzbdv> zze = zzI();

    static {
        zzbdy zzbdyVar = new zzbdy();
        zzb = zzbdyVar;
        zzans.zzM(zzbdy.class, zzbdyVar);
    }

    private zzbdy() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zze", zzbdv.class});
        }
        if (i2 == 3) {
            return new zzbdy();
        }
        zzbdw zzbdwVar = null;
        if (i2 == 4) {
            return new zzbdx(zzbdwVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
