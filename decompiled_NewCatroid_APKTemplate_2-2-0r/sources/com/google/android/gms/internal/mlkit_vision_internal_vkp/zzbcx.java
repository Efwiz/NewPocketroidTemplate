package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbcx extends zzans<zzbcx, zzbcw> implements zzapg {
    private static final zzbcx zzb;
    private zzbcu zze;
    private zzbek zzf;

    static {
        zzbcx zzbcxVar = new zzbcx();
        zzb = zzbcxVar;
        zzans.zzM(zzbcx.class, zzbcxVar);
    }

    private zzbcx() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\t\u0002\t", new Object[]{"zze", "zzf"});
        }
        if (i2 == 3) {
            return new zzbcx();
        }
        zzbcv zzbcvVar = null;
        if (i2 == 4) {
            return new zzbcw(zzbcvVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
