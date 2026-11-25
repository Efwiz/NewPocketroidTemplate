package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbcb extends zzans<zzbcb, zzbca> implements zzapg {
    private static final zzbcb zzb;
    private int zze;
    private int zzf;
    private boolean zzi;
    private int zzg = 100;
    private int zzh = -100;
    private float zzj = 40.0f;

    static {
        zzbcb zzbcbVar = new zzbcb();
        zzb = zzbcbVar;
        zzans.zzM(zzbcb.class, zzbcbVar);
    }

    private zzbcb() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001င\u0000\u0002င\u0001\u0003င\u0002\u0004ဇ\u0003\u0005ခ\u0004", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj"});
        }
        if (i2 == 3) {
            return new zzbcb();
        }
        zzbbz zzbbzVar = null;
        if (i2 == 4) {
            return new zzbca(zzbbzVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
