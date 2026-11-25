package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbek extends zzans<zzbek, zzbej> implements zzapg {
    private static final zzbek zzb;
    private Object zzf;
    private int zze = 0;
    private String zzg = "";
    private String zzh = "";
    private String zzi = "";

    static {
        zzbek zzbekVar = new zzbek();
        zzb = zzbekVar;
        zzans.zzM(zzbek.class, zzbekVar);
    }

    private zzbek() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0005\u0001\u0000\u0001\u0005\u0005\u0000\u0000\u0000\u0001Ȉ\u0002Ȼ\u0000\u0003=\u0000\u0004Ȉ\u0005Ȉ", new Object[]{"zzf", "zze", "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzbek();
        }
        zzbei zzbeiVar = null;
        if (i2 == 4) {
            return new zzbej(zzbeiVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
