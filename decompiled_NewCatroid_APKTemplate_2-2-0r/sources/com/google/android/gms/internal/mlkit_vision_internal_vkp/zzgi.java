package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzgi extends zzans<zzgi, zzgh> implements zzapg {
    private static final zzgi zzb;
    private zzaob<zzga> zze = zzI();

    static {
        zzgi zzgiVar = new zzgi();
        zzb = zzgiVar;
        zzans.zzM(zzgi.class, zzgiVar);
    }

    private zzgi() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zze", zzga.class});
        }
        if (i2 == 3) {
            return new zzgi();
        }
        zzgg zzggVar = null;
        if (i2 == 4) {
            return new zzgh(zzggVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
