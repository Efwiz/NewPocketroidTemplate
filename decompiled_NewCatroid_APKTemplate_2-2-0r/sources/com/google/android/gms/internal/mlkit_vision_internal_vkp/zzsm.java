package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzsm extends zzans<zzsm, zzsl> implements zzapg {
    private static final zzsm zzb;
    private int zze;
    private int zzf;

    static {
        zzsm zzsmVar = new zzsm();
        zzb = zzsmVar;
        zzans.zzM(zzsm.class, zzsmVar);
    }

    private zzsm() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဌ\u0000", new Object[]{"zze", "zzf", zzqb.zza});
        }
        if (i2 == 3) {
            return new zzsm();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zzsl(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
