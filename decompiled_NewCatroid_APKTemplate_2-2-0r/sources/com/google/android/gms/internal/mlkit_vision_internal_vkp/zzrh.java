package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzrh extends zzans<zzrh, zzrg> implements zzapg {
    private static final zzrh zzb;
    private int zze;
    private int zzf;

    static {
        zzrh zzrhVar = new zzrh();
        zzb = zzrhVar;
        zzans.zzM(zzrh.class, zzrhVar);
    }

    private zzrh() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဌ\u0000", new Object[]{"zze", "zzf", zzrf.zza});
        }
        if (i2 == 3) {
            return new zzrh();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zzrg(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
