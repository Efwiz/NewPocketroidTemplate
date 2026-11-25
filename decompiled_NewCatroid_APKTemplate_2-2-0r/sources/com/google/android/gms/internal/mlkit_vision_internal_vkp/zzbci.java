package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbci extends zzans<zzbci, zzbcf> implements zzapg {
    private static final zzbci zzb;
    private int zze;
    private String zzf = "visionkit-pa.googleapis.com";
    private String zzg = "";
    private zzaob<zzbch> zzh = zzI();

    static {
        zzbci zzbciVar = new zzbci();
        zzb = zzbciVar;
        zzans.zzM(zzbci.class, zzbciVar);
    }

    private zzbci() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0001\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003\u001b", new Object[]{"zze", "zzf", "zzg", "zzh", zzbch.class});
        }
        if (i2 == 3) {
            return new zzbci();
        }
        zzbce zzbceVar = null;
        if (i2 == 4) {
            return new zzbcf(zzbceVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
