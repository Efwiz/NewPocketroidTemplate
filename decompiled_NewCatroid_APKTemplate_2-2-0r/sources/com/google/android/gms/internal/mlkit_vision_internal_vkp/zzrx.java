package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzrx extends zzans<zzrx, zzrw> implements zzapg {
    private static final zzrx zzb;
    private int zze;
    private int zzf;
    private int zzg;

    static {
        zzrx zzrxVar = new zzrx();
        zzb = zzrxVar;
        zzans.zzM(zzrx.class, zzrxVar);
    }

    private zzrx() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဌ\u0001", new Object[]{"zze", "zzf", zzul.zza, "zzg", zzqb.zza});
        }
        if (i2 == 3) {
            return new zzrx();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zzrw(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
