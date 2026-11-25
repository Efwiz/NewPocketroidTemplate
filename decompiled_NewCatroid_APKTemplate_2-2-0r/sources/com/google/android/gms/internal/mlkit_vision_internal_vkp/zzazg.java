package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzazg extends zzans<zzazg, zzazb> implements zzapg {
    private static final zzazg zzb;
    private int zze;
    private zzaza zzf;
    private zzaob<zzazf> zzg = zzI();
    private float zzh;

    static {
        zzazg zzazgVar = new zzazg();
        zzb = zzazgVar;
        zzans.zzM(zzazg.class, zzazgVar);
    }

    private zzazg() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0001\u0000\u0001ဉ\u0000\u0002\u001b\u0003ခ\u0001", new Object[]{"zze", "zzf", "zzg", zzazf.class, "zzh"});
        }
        if (i2 == 3) {
            return new zzazg();
        }
        zzayy zzayyVar = null;
        if (i2 == 4) {
            return new zzazb(zzayyVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
