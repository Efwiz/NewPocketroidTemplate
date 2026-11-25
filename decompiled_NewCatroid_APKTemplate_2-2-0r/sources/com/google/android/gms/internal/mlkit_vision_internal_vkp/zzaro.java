package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaro extends zzans<zzaro, zzarn> implements zzapg {
    private static final zzaro zzb;
    private int zze;
    private int zzf;
    private zzato zzg;
    private zzasy zzh;
    private zzata zzi;

    static {
        zzaro zzaroVar = new zzaro();
        zzb = zzaroVar;
        zzans.zzM(zzaro.class, zzaroVar);
    }

    private zzaro() {
    }

    public static zzaro zze() {
        return zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0005\u0004\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဉ\u0001\u0003ဉ\u0002\u0005ဉ\u0003", new Object[]{"zze", "zzf", zzask.zza, "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzaro();
        }
        zzarm zzarmVar = null;
        if (i2 == 4) {
            return new zzarn(zzarmVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
