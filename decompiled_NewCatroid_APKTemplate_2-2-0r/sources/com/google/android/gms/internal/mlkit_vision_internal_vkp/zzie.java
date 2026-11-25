package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzie extends zzans<zzie, zzid> implements zzapg {
    private static final zzie zzb;
    private int zze;
    private zzhi zzf;
    private zzhi zzg;

    static {
        zzie zzieVar = new zzie();
        zzb = zzieVar;
        zzans.zzM(zzie.class, zzieVar);
    }

    private zzie() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzie();
        }
        zzib zzibVar = null;
        if (i2 == 4) {
            return new zzid(zzibVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
