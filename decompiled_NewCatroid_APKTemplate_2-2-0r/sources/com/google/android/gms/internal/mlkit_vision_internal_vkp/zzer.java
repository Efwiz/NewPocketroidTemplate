package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzer extends zzans<zzer, zzeq> implements zzapg {
    private static final zzer zzb;
    private int zze;
    private boolean zzf;
    private float zzg = 0.2f;
    private zzaob<zzeu> zzh = zzI();

    static {
        zzer zzerVar = new zzer();
        zzb = zzerVar;
        zzans.zzM(zzer.class, zzerVar);
    }

    private zzer() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0004\u0003\u0000\u0001\u0000\u0001ဇ\u0000\u0002ခ\u0001\u0004\u001b", new Object[]{"zze", "zzf", "zzg", "zzh", zzeu.class});
        }
        if (i2 == 3) {
            return new zzer();
        }
        zzep zzepVar = null;
        if (i2 == 4) {
            return new zzeq(zzepVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
