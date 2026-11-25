package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzawv extends zzans<zzawv, zzawu> implements zzapg {
    private static final zzawv zzb;
    private zzail zze;
    private double zzf;
    private double zzg;

    static {
        zzawv zzawvVar = new zzawv();
        zzb = zzawvVar;
        zzans.zzM(zzawv.class, zzawvVar);
    }

    private zzawv() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\t\u0002\u0000\u0003\u0000", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzawv();
        }
        zzawt zzawtVar = null;
        if (i2 == 4) {
            return new zzawu(zzawtVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
