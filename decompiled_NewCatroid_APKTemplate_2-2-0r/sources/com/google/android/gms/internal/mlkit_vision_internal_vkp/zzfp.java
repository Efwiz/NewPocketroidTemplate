package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzfp extends zzans<zzfp, zzfo> implements zzapg {
    private static final zzfp zzb;
    private zzaob<zzfm> zze = zzI();

    static {
        zzfp zzfpVar = new zzfp();
        zzb = zzfpVar;
        zzans.zzM(zzfp.class, zzfpVar);
    }

    private zzfp() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zze", zzfm.class});
        }
        if (i2 == 3) {
            return new zzfp();
        }
        zzfn zzfnVar = null;
        if (i2 == 4) {
            return new zzfo(zzfnVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
