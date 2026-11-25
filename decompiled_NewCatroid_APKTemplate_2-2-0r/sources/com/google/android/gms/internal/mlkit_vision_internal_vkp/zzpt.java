package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzpt extends zzans<zzpt, zzpp> implements zzapg {
    private static final zzpt zzb;
    private int zze;
    private zzqv zzf;
    private zzrs zzg;
    private zzaob<zzps> zzh = zzI();
    private zzaob<zzps> zzi = zzI();
    private long zzj;

    static {
        zzpt zzptVar = new zzpt();
        zzb = zzptVar;
        zzans.zzM(zzpt.class, zzptVar);
    }

    private zzpt() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0002\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003\u001b\u0004\u001b\u0005ဃ\u0002", new Object[]{"zze", "zzf", "zzg", "zzh", zzps.class, "zzi", zzps.class, "zzj"});
        }
        if (i2 == 3) {
            return new zzpt();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zzpp(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
