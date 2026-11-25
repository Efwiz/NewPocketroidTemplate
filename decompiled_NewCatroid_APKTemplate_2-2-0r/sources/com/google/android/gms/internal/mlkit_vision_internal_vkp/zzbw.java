package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbw extends zzans<zzbw, zzbv> implements zzapg {
    private static final zzbw zzb;
    private int zze;
    private zzaob<zzgo> zzf = zzI();
    private zzbt zzg;

    static {
        zzbw zzbwVar = new zzbw();
        zzb = zzbwVar;
        zzans.zzM(zzbw.class, zzbwVar);
    }

    private zzbw() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\u001b\u0002ဉ\u0000", new Object[]{"zze", "zzf", zzgo.class, "zzg"});
        }
        if (i2 == 3) {
            return new zzbw();
        }
        zzbu zzbuVar = null;
        if (i2 == 4) {
            return new zzbv(zzbuVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
