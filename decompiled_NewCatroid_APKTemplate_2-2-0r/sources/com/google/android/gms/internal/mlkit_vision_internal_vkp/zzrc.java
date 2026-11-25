package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzrc extends zzans<zzrc, zzrb> implements zzapg {
    private static final zzrc zzb;
    private int zze;
    private zzre zzf;
    private int zzg;
    private int zzh;

    static {
        zzrc zzrcVar = new zzrc();
        zzb = zzrcVar;
        zzans.zzM(zzrc.class, zzrcVar);
    }

    private zzrc() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဌ\u0001\u0003ဌ\u0002", new Object[]{"zze", "zzf", "zzg", zzul.zza, "zzh", zzqb.zza});
        }
        if (i2 == 3) {
            return new zzrc();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zzrb(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
