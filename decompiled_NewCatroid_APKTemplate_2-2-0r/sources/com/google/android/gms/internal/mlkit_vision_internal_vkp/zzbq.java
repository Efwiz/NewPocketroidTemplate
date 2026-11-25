package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbq extends zzans<zzbq, zzbp> implements zzapg {
    private static final zzbq zzb;
    private int zze;
    private float zzf;
    private float zzg;
    private float zzh;
    private float zzi;

    static {
        zzbq zzbqVar = new zzbq();
        zzb = zzbqVar;
        zzans.zzM(zzbq.class, zzbqVar);
    }

    private zzbq() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ခ\u0000\u0002ခ\u0001\u0003ခ\u0002\u0004ခ\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzbq();
        }
        zzbo zzboVar = null;
        if (i2 == 4) {
            return new zzbp(zzboVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
