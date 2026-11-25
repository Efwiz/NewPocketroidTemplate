package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbdd extends zzans<zzbdd, zzbdc> implements zzapg {
    private static final zzbdd zzb;
    private zzaob<zzbcx> zze = zzI();
    private zzaob<zzbda> zzf = zzI();

    static {
        zzbdd zzbddVar = new zzbdd();
        zzb = zzbddVar;
        zzans.zzM(zzbdd.class, zzbddVar);
    }

    private zzbdd() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0002\u0000\u0001\u001b\u0002\u001b", new Object[]{"zze", zzbcx.class, "zzf", zzbda.class});
        }
        if (i2 == 3) {
            return new zzbdd();
        }
        zzbdb zzbdbVar = null;
        if (i2 == 4) {
            return new zzbdc(zzbdbVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
