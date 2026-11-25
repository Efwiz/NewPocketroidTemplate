package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zznn extends zzans<zznn, zznk> implements zzapg {
    private static final zznn zzb;
    private int zze;
    private zznm zzf;
    private int zzg;
    private zzqa zzh;

    static {
        zznn zznnVar = new zznn();
        zzb = zznnVar;
        zzans.zzM(zznn.class, zznnVar);
    }

    private zznn() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဋ\u0001\u0003ဉ\u0002", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zznn();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zznk(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
