package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzyc extends zzans<zzyc, zzxy> implements zzapg {
    private static final zzyc zzb;
    private int zze;
    private zzaob<zzya> zzf = zzI();
    private int zzg;
    private int zzh;

    static {
        zzyc zzycVar = new zzyc();
        zzb = zzycVar;
        zzans.zzM(zzyc.class, zzycVar);
    }

    private zzyc() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0001\u0000\u0001\u001b\u0002ဌ\u0000\u0003ဌ\u0001", new Object[]{"zze", "zzf", zzya.class, "zzg", zzyb.zza, "zzh", zzxw.zza});
        }
        if (i2 == 3) {
            return new zzyc();
        }
        zzxx zzxxVar = null;
        if (i2 == 4) {
            return new zzxy(zzxxVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
