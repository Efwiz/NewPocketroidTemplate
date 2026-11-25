package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzalj extends zzans<zzalj, zzali> implements zzapg {
    private static final zzalj zzb;
    private int zze;
    private float zzg;
    private String zzf = "";
    private int zzh = 1;

    static {
        zzalj zzaljVar = new zzalj();
        zzb = zzaljVar;
        zzans.zzM(zzalj.class, zzaljVar);
    }

    private zzalj() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဈ\u0000\u0002ခ\u0001\u0003င\u0002", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzalj();
        }
        zzalh zzalhVar = null;
        if (i2 == 4) {
            return new zzali(zzalhVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
