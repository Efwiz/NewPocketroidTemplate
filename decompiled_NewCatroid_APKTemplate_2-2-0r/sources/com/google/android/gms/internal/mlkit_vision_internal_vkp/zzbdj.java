package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbdj extends zzans<zzbdj, zzbdi> implements zzapg {
    private static final zzbdj zzb;
    private int zze = 0;
    private Object zzf;

    static {
        zzbdj zzbdjVar = new zzbdj();
        zzb = zzbdjVar;
        zzans.zzM(zzbdj.class, zzbdjVar);
    }

    private zzbdj() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0002\u0001\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001<\u0000\u0002<\u0000", new Object[]{"zzf", "zze", zzbdm.class, zzbds.class});
        }
        if (i2 == 3) {
            return new zzbdj();
        }
        zzbdh zzbdhVar = null;
        if (i2 == 4) {
            return new zzbdi(zzbdhVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
