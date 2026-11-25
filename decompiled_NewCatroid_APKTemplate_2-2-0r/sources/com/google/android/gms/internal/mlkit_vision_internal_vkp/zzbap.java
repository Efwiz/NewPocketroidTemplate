package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbap extends zzans<zzbap, zzbao> implements zzapg {
    private static final zzbap zzb;
    private int zze;
    private int zzf = 0;
    private Object zzg;

    static {
        zzbap zzbapVar = new zzbap();
        zzb = zzbapVar;
        zzans.zzM(zzbap.class, zzbapVar);
    }

    private zzbap() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0001\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ျ\u0000\u0002ွ\u0000", new Object[]{"zzg", "zzf", "zze"});
        }
        if (i2 == 3) {
            return new zzbap();
        }
        zzban zzbanVar = null;
        if (i2 == 4) {
            return new zzbao(zzbanVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
