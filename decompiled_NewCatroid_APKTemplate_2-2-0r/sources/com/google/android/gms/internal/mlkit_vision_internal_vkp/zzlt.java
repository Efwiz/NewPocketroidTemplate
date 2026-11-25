package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzlt extends zzans<zzlt, zzls> implements zzapg {
    private static final zzlt zzb;
    private int zze;
    private Object zzg;
    private int zzf = 0;
    private zzaob<zzlt> zzh = zzI();

    static {
        zzlt zzltVar = new zzlt();
        zzb = zzltVar;
        zzans.zzM(zzlt.class, zzltVar);
    }

    private zzlt() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0001\u0001\u0001\u0004\u0004\u0000\u0001\u0000\u0001ျ\u0000\u0002ဳ\u0000\u0003ြ\u0000\u0004\u001b", new Object[]{"zzg", "zzf", "zze", zzlx.class, "zzh", zzlt.class});
        }
        if (i2 == 3) {
            return new zzlt();
        }
        zzlr zzlrVar = null;
        if (i2 == 4) {
            return new zzls(zzlrVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
