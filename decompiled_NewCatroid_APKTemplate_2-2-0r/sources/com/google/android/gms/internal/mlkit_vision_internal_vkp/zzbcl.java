package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbcl extends zzans<zzbcl, zzbck> implements zzapg {
    private static final zzbcl zzb;
    private int zze = 0;
    private Object zzf;

    static {
        zzbcl zzbclVar = new zzbcl();
        zzb = zzbclVar;
        zzans.zzM(zzbcl.class, zzbclVar);
    }

    private zzbcl() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0003\u0001\u0000\u0002\u0004\u0003\u0000\u0000\u0000\u0002<\u0000\u0003<\u0000\u0004<\u0000", new Object[]{"zzf", "zze", zzbee.class, zzbdp.class, zzbez.class});
        }
        if (i2 == 3) {
            return new zzbcl();
        }
        zzbcj zzbcjVar = null;
        if (i2 == 4) {
            return new zzbck(zzbcjVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
