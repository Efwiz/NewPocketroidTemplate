package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzawp extends zzans<zzawp, zzawo> implements zzapg {
    private static final zzawp zzb;
    private int zze;
    private int zzf;
    private int zzg;
    private int zzh = 1;
    private int zzi = 1;

    static {
        zzawp zzawpVar = new zzawp();
        zzb = zzawpVar;
        zzans.zzM(zzawp.class, zzawpVar);
    }

    private zzawp() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001င\u0000\u0002င\u0001\u0003င\u0002\u0004င\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzawp();
        }
        zzavr zzavrVar = null;
        if (i2 == 4) {
            return new zzawo(zzavrVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
