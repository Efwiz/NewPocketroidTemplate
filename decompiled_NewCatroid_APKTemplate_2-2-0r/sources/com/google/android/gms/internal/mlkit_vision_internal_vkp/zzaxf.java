package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaxf extends zzans<zzaxf, zzaxe> implements zzapg {
    private static final zzaxf zzb;
    private zzaob<zzaxc> zze = zzI();
    private zzaob<zzayq> zzf = zzI();

    static {
        zzaxf zzaxfVar = new zzaxf();
        zzb = zzaxfVar;
        zzans.zzM(zzaxf.class, zzaxfVar);
    }

    private zzaxf() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0002\u0000\u0001\u001b\u0002\u001b", new Object[]{"zze", zzaxc.class, "zzf", zzayq.class});
        }
        if (i2 == 3) {
            return new zzaxf();
        }
        zzaxd zzaxdVar = null;
        if (i2 == 4) {
            return new zzaxe(zzaxdVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
