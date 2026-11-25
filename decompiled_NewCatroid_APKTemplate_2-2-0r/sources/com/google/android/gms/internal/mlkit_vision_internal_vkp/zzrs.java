package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzrs extends zzans<zzrs, zzrp> implements zzapg {
    private static final zzrs zzb;
    private int zze;
    private zzro zzf;
    private zzrr zzg;
    private zzrr zzh;
    private boolean zzi;

    static {
        zzrs zzrsVar = new zzrs();
        zzb = zzrsVar;
        zzans.zzM(zzrs.class, zzrsVar);
    }

    private zzrs() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002\u0004ဇ\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzrs();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zzrp(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
