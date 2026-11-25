package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbeb extends zzans<zzbeb, zzbea> implements zzapg {
    private static final zzbeb zzb;
    private zzbeh zze;
    private zzbew zzf;
    private zzbdy zzg;
    private zzbdd zzh;
    private zzbcr zzi;
    private zzbeq zzj;
    private zzbdj zzk;

    static {
        zzbeb zzbebVar = new zzbeb();
        zzb = zzbebVar;
        zzans.zzM(zzbeb.class, zzbebVar);
    }

    private zzbeb() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0007\u0000\u0000\u0001\u0007\u0007\u0000\u0000\u0000\u0001\t\u0002\t\u0003\t\u0004\t\u0005\t\u0006\t\u0007\t", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk"});
        }
        if (i2 == 3) {
            return new zzbeb();
        }
        zzbdz zzbdzVar = null;
        if (i2 == 4) {
            return new zzbea(zzbdzVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
