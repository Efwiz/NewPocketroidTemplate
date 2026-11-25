package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzben extends zzans<zzben, zzbem> implements zzapg {
    private static final zzben zzb;
    private long zzf;
    private long zzg;
    private zzamk zze = zzamk.zzb;
    private zzaob<zzbdg> zzh = zzI();

    static {
        zzben zzbenVar = new zzben();
        zzb = zzbenVar;
        zzans.zzM(zzben.class, zzbenVar);
    }

    private zzben() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0001\u0000\u0001\n\u0002\u0002\u0003\u0002\u0004\u001b", new Object[]{"zze", "zzf", "zzg", "zzh", zzbdg.class});
        }
        if (i2 == 3) {
            return new zzben();
        }
        zzbel zzbelVar = null;
        if (i2 == 4) {
            return new zzbem(zzbelVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
