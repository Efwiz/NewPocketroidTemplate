package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbay extends zzans<zzbay, zzbax> implements zzapg {
    private static final zzbay zzb;
    private int zze;
    private Object zzg;
    private int zzf = 0;
    private String zzh = "";
    private String zzi = "";
    private zzamk zzj = zzamk.zzb;

    static {
        zzbay zzbayVar = new zzbay();
        zzb = zzbayVar;
        zzans.zzM(zzbay.class, zzbayVar);
    }

    private zzbay() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0005\u0001\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ြ\u0000\u0004ြ\u0000\u0005ည\u0004", new Object[]{"zzg", "zzf", "zze", "zzh", "zzi", zzbas.class, zzbbm.class, "zzj"});
        }
        if (i2 == 3) {
            return new zzbay();
        }
        zzbaw zzbawVar = null;
        if (i2 == 4) {
            return new zzbax(zzbawVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
