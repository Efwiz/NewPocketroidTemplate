package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zznm extends zzans<zznm, zznl> implements zzapg {
    private static final zznm zzb;
    private int zze;
    private int zzf;
    private boolean zzg;
    private zzqr zzh;
    private zzsv zzi;

    static {
        zznm zznmVar = new zznm();
        zzb = zznmVar;
        zzans.zzM(zznm.class, zznmVar);
    }

    private zznm() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဉ\u0002\u0004ဉ\u0003", new Object[]{"zze", "zzf", zzqb.zza, "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zznm();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zznl(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
