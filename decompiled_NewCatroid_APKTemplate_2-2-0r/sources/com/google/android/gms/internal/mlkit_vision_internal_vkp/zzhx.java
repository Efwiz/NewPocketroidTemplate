package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzhx extends zzans<zzhx, zzhq> implements zzapg {
    private static final zzhx zzb;
    private int zze;
    private Object zzg;
    private int zzh;
    private int zzi;
    private int zzf = 0;
    private zzaob<zzhs> zzj = zzI();

    static {
        zzhx zzhxVar = new zzhx();
        zzb = zzhxVar;
        zzans.zzM(zzhx.class, zzhxVar);
    }

    private zzhx() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0005\u0001\u0001\u0001\u0005\u0005\u0000\u0001\u0000\u0001ွ\u0000\u0002င\u0002\u0003င\u0003\u0004ြ\u0000\u0005\u001b", new Object[]{"zzg", "zzf", "zze", "zzh", "zzi", zzhw.class, "zzj", zzhs.class});
        }
        if (i2 == 3) {
            return new zzhx();
        }
        zzhp zzhpVar = null;
        if (i2 == 4) {
            return new zzhq(zzhpVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
