package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaf extends zzans<zzaf, zzae> implements zzapg {
    private static final zzaf zzb;
    private int zze;
    private zzaw zzf;
    private zzalj zzg;
    private String zzh = "";

    static {
        zzaf zzafVar = new zzaf();
        zzb = zzafVar;
        zzans.zzM(zzaf.class, zzafVar);
    }

    private zzaf() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဈ\u0002\u0003ဉ\u0001", new Object[]{"zze", "zzf", "zzh", "zzg"});
        }
        if (i2 == 3) {
            return new zzaf();
        }
        zzad zzadVar = null;
        if (i2 == 4) {
            return new zzae(zzadVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
