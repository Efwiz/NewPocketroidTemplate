package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzawz extends zzans<zzawz, zzawx> implements zzapg {
    private static final zzawz zzb;
    private zzaoz<Integer, zzaxj> zze = zzaoz.zza();

    static {
        zzawz zzawzVar = new zzawz();
        zzb = zzawzVar;
        zzans.zzM(zzawz.class, zzawzVar);
    }

    private zzawz() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u00012", new Object[]{"zze", zzawy.zza});
        }
        if (i2 == 3) {
            return new zzawz();
        }
        zzaww zzawwVar = null;
        if (i2 == 4) {
            return new zzawx(zzawwVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
