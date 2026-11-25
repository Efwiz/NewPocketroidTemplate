package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbch extends zzans<zzbch, zzbcg> implements zzapg {
    private static final zzbch zzb;
    private int zze;
    private String zzf = "";
    private zzamk zzg = zzamk.zzb;

    static {
        zzbch zzbchVar = new zzbch();
        zzb = zzbchVar;
        zzans.zzM(zzbch.class, zzbchVar);
    }

    private zzbch() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဈ\u0000\u0002ည\u0001", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzbch();
        }
        zzbce zzbceVar = null;
        if (i2 == 4) {
            return new zzbcg(zzbceVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
