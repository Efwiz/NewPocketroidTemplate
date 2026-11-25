package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzfz extends zzans<zzfz, zzfy> implements zzapg {
    private static final zzfz zzb;
    private int zze;
    private int zzf;
    private int zzg;

    static {
        zzfz zzfzVar = new zzfz();
        zzb = zzfzVar;
        zzans.zzM(zzfz.class, zzfzVar);
    }

    private zzfz() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001င\u0000\u0002င\u0001", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzfz();
        }
        zzfv zzfvVar = null;
        if (i2 == 4) {
            return new zzfy(zzfvVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
