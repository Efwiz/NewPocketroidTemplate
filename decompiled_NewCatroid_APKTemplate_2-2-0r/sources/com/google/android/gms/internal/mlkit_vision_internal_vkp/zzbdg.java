package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbdg extends zzans<zzbdg, zzbdf> implements zzapg {
    private static final zzbdg zzb;
    private int zze;
    private int zzf;
    private int zzg;
    private String zzh = "";

    static {
        zzbdg zzbdgVar = new zzbdg();
        zzb = zzbdgVar;
        zzans.zzM(zzbdg.class, zzbdgVar);
    }

    private zzbdg() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u000b\u0002\u000b\u0003\u000b\u0004Ȉ", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzbdg();
        }
        zzbde zzbdeVar = null;
        if (i2 == 4) {
            return new zzbdf(zzbdeVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
