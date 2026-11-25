package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzsq extends zzans<zzsq, zzsp> implements zzapg {
    private static final zzsq zzb;
    private int zze;
    private zzqv zzf;
    private zzsv zzg;
    private zzqr zzh;
    private int zzi;
    private float zzj;

    static {
        zzsq zzsqVar = new zzsq();
        zzb = zzsqVar;
        zzans.zzM(zzsq.class, zzsqVar);
    }

    private zzsq() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002\u0004ဋ\u0003\u0005ခ\u0004", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj"});
        }
        if (i2 == 3) {
            return new zzsq();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zzsp(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
