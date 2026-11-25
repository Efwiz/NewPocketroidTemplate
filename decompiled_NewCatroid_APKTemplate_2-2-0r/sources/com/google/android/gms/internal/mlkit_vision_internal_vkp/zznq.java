package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zznq extends zzans<zznq, zznp> implements zzapg {
    private static final zznq zzb;
    private int zze;
    private int zzf;
    private boolean zzg;
    private boolean zzh;
    private zzqr zzi;
    private zzti zzj;

    static {
        zznq zznqVar = new zznq();
        zzb = zznqVar;
        zzans.zzM(zznq.class, zznqVar);
    }

    private zznq() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဇ\u0002\u0004ဉ\u0003\u0005ဉ\u0004", new Object[]{"zze", "zzf", zzqb.zza, "zzg", "zzh", "zzi", "zzj"});
        }
        if (i2 == 3) {
            return new zznq();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zznp(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
