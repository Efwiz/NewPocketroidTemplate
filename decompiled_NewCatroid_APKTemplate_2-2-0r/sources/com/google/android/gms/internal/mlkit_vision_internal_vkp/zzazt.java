package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzazt extends zzans<zzazt, zzazs> implements zzapg {
    private static final zzazt zzb;
    private int zze;
    private float zzf = 10.0f;
    private float zzg = 10.0f;
    private float zzh = 5.0f;
    private float zzi = 5.0f;
    private float zzj = 10.0f;
    private float zzk = 10.0f;

    static {
        zzazt zzaztVar = new zzazt();
        zzb = zzaztVar;
        zzans.zzM(zzazt.class, zzaztVar);
    }

    private zzazt() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001ခ\u0000\u0002ခ\u0001\u0003ခ\u0002\u0004ခ\u0003\u0005ခ\u0004\u0006ခ\u0005", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk"});
        }
        if (i2 == 3) {
            return new zzazt();
        }
        zzazn zzaznVar = null;
        if (i2 == 4) {
            return new zzazs(zzaznVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
