package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzsa extends zzans<zzsa, zzry> implements zzapg {
    private static final zzsa zzb;
    private int zze;
    private int zzf;
    private float zzg;
    private int zzh;
    private int zzi;

    static {
        zzsa zzsaVar = new zzsa();
        zzb = zzsaVar;
        zzans.zzM(zzsa.class, zzsaVar);
    }

    private zzsa() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဌ\u0000\u0002ခ\u0001\u0003င\u0002\u0004ဋ\u0003", new Object[]{"zze", "zzf", zzrz.zza, "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzsa();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zzry(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
