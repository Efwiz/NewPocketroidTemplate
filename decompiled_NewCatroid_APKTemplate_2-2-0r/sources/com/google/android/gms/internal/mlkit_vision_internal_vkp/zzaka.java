package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaka extends zzans<zzaka, zzajz> implements zzapg {
    private static final zzaka zzb;
    private int zze;
    private float zzg;
    private float zzh;
    private float zzi;
    private float zzj;
    private int zzf = 1;
    private float zzk = 1.0f;

    static {
        zzaka zzakaVar = new zzaka();
        zzb = zzakaVar;
        zzans.zzM(zzaka.class, zzakaVar);
    }

    private zzaka() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001င\u0000\u0002ခ\u0001\u0003ခ\u0002\u0004ခ\u0003\u0005ခ\u0004\u0006ခ\u0005", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk"});
        }
        if (i2 == 3) {
            return new zzaka();
        }
        zzaiv zzaivVar = null;
        if (i2 == 4) {
            return new zzajz(zzaivVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
