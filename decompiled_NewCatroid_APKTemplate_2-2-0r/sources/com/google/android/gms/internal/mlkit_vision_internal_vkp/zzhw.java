package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzhw extends zzans<zzhw, zzhv> implements zzapg {
    private static final zzhw zzb;
    private zzaob<zzhu> zze = zzI();

    static {
        zzhw zzhwVar = new zzhw();
        zzb = zzhwVar;
        zzans.zzM(zzhw.class, zzhwVar);
    }

    private zzhw() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zze", zzhu.class});
        }
        if (i2 == 3) {
            return new zzhw();
        }
        zzhp zzhpVar = null;
        if (i2 == 4) {
            return new zzhv(zzhpVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
