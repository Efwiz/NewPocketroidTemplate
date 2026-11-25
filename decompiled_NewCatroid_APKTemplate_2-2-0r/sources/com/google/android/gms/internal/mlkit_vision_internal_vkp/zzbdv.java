package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbdv extends zzans<zzbdv, zzbdu> implements zzapg {
    private static final zzbdv zzb;
    private zzbek zze;
    private double zzf;

    static {
        zzbdv zzbdvVar = new zzbdv();
        zzb = zzbdvVar;
        zzans.zzM(zzbdv.class, zzbdvVar);
    }

    private zzbdv() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\t\u0002\u0000", new Object[]{"zze", "zzf"});
        }
        if (i2 == 3) {
            return new zzbdv();
        }
        zzbdt zzbdtVar = null;
        if (i2 == 4) {
            return new zzbdu(zzbdtVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
