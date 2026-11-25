package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzps extends zzans<zzps, zzpq> implements zzapg {
    private static final zzps zzb;
    private int zze;
    private int zzf;
    private zzany zzg = zzG();

    static {
        zzps zzpsVar = new zzps();
        zzb = zzpsVar;
        zzans.zzM(zzps.class, zzpsVar);
    }

    private zzps() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001ဌ\u0000\u0002\u0016", new Object[]{"zze", "zzf", zzpr.zza, "zzg"});
        }
        if (i2 == 3) {
            return new zzps();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zzpq(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
