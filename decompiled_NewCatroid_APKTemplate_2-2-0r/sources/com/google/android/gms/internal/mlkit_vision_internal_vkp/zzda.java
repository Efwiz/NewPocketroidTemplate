package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzda extends zzans<zzda, zzcv> implements zzapg {
    private static final zzda zzb;
    private int zze;
    private zzcx zzf;
    private zzcz zzg;
    private zzhi zzh;
    private zzhi zzi;

    static {
        zzda zzdaVar = new zzda();
        zzb = zzdaVar;
        zzans.zzM(zzda.class, zzdaVar);
    }

    private zzda() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002\u0004ဉ\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzda();
        }
        zzcu zzcuVar = null;
        if (i2 == 4) {
            return new zzcv(zzcuVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
