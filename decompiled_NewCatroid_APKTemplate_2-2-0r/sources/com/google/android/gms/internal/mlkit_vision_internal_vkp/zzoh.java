package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzoh extends zzans<zzoh, zzog> implements zzapg {
    private static final zzoh zzb;
    private int zze;
    private zzqv zzf;
    private zzrs zzg;
    private long zzh;
    private float zzi;
    private zzqr zzj;

    static {
        zzoh zzohVar = new zzoh();
        zzb = zzohVar;
        zzans.zzM(zzoh.class, zzohVar);
    }

    private zzoh() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဃ\u0002\u0004ခ\u0003\u0005ဉ\u0004", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj"});
        }
        if (i2 == 3) {
            return new zzoh();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zzog(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
