package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzln extends zzans<zzln, zzlm> implements zzapg {
    private static final zzln zzb;
    private zzaob<zzll> zze = zzI();

    static {
        zzln zzlnVar = new zzln();
        zzb = zzlnVar;
        zzans.zzM(zzln.class, zzlnVar);
    }

    private zzln() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zze", zzll.class});
        }
        if (i2 == 3) {
            return new zzln();
        }
        zzlj zzljVar = null;
        if (i2 == 4) {
            return new zzlm(zzljVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
