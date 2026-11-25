package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzajp extends zzans<zzajp, zzajo> implements zzapg {
    private static final zzajp zzb;
    private int zze;
    private float zzf = 1.5f;
    private float zzg = 3.0f;
    private float zzh = 3.0f;
    private float zzi = 2.0f;
    private float zzj = 0.5f;
    private float zzk = 1.5f;
    private float zzl = 1.7f;
    private float zzm = 2.0f;
    private boolean zzn;

    static {
        zzajp zzajpVar = new zzajp();
        zzb = zzajpVar;
        zzans.zzM(zzajp.class, zzajpVar);
    }

    private zzajp() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\t\u0000\u0001\u0001\t\t\u0000\u0000\u0000\u0001ခ\u0000\u0002ခ\u0001\u0003ခ\u0002\u0004ခ\u0003\u0005ခ\u0004\u0006ခ\u0005\u0007ခ\u0006\bခ\u0007\tဇ\b", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", "zzn"});
        }
        if (i2 == 3) {
            return new zzajp();
        }
        zzaiv zzaivVar = null;
        if (i2 == 4) {
            return new zzajo(zzaivVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
