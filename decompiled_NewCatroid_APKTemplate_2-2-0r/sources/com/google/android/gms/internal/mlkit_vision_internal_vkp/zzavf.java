package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzavf extends zzans<zzavf, zzavd> implements zzapg {
    private static final zzavf zzb;
    private int zze;
    private int zzf;
    private float zzg;

    static {
        zzavf zzavfVar = new zzavf();
        zzb = zzavfVar;
        zzans.zzM(zzavf.class, zzavfVar);
    }

    private zzavf() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဌ\u0000\u0002ခ\u0001", new Object[]{"zze", "zzf", zzave.zza, "zzg"});
        }
        if (i2 == 3) {
            return new zzavf();
        }
        zzatr zzatrVar = null;
        if (i2 == 4) {
            return new zzavd(zzatrVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
