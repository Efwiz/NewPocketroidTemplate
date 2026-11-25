package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcz extends zzans<zzcz, zzcy> implements zzapg {
    private static final zzcz zzb;
    private int zze;
    private int zzf = -1;
    private float zzg = 1.0f;
    private int zzh;

    static {
        zzcz zzczVar = new zzcz();
        zzb = zzczVar;
        zzans.zzM(zzcz.class, zzczVar);
    }

    private zzcz() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001င\u0000\u0002ခ\u0001\u0003င\u0002", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzcz();
        }
        zzcu zzcuVar = null;
        if (i2 == 4) {
            return new zzcy(zzcuVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
