package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzqr extends zzans<zzqr, zzqp> implements zzapg {
    private static final zzqr zzb;
    private int zze;
    private int zzf;
    private int zzg;
    private int zzh;
    private boolean zzi;

    static {
        zzqr zzqrVar = new zzqr();
        zzb = zzqrVar;
        zzans.zzM(zzqr.class, zzqrVar);
    }

    private zzqr() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဋ\u0001\u0003ဋ\u0002\u0004ဇ\u0003", new Object[]{"zze", "zzf", zzqq.zza, "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzqr();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zzqp(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
