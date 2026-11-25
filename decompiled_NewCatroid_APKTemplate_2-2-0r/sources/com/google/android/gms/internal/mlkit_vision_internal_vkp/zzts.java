package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzts extends zzans<zzts, zztp> implements zzapg {
    private static final zzts zzb;
    private int zze;
    private int zzf;
    private int zzg;
    private int zzh;

    static {
        zzts zztsVar = new zzts();
        zzb = zztsVar;
        zzans.zzM(zzts.class, zztsVar);
    }

    private zzts() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဌ\u0001\u0003ဌ\u0002", new Object[]{"zze", "zzf", zztq.zza, "zzg", zztr.zza, "zzh", zztr.zza});
        }
        if (i2 == 3) {
            return new zzts();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zztp(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
