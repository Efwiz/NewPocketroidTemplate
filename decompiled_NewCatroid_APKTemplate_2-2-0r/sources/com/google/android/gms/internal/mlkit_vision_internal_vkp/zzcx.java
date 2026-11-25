package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcx extends zzans<zzcx, zzcw> implements zzapg {
    private static final zzcx zzb;
    private int zze;
    private zzhi zzf;

    static {
        zzcx zzcxVar = new zzcx();
        zzb = zzcxVar;
        zzans.zzM(zzcx.class, zzcxVar);
    }

    private zzcx() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0001\u0004\u0004\u0001\u0000\u0000\u0000\u0004ဉ\u0000", new Object[]{"zze", "zzf"});
        }
        if (i2 == 3) {
            return new zzcx();
        }
        zzcu zzcuVar = null;
        if (i2 == 4) {
            return new zzcw(zzcuVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
