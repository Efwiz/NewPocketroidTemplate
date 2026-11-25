package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzd extends zzans<zzd, zzc> implements zzapg {
    private static final zzd zzb;
    private zzaob<zzg> zze = zzI();

    static {
        zzd zzdVar = new zzd();
        zzb = zzdVar;
        zzans.zzM(zzd.class, zzdVar);
    }

    private zzd() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zze", zzg.class});
        }
        if (i2 == 3) {
            return new zzd();
        }
        zzb zzbVar = null;
        if (i2 == 4) {
            return new zzc(zzbVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
