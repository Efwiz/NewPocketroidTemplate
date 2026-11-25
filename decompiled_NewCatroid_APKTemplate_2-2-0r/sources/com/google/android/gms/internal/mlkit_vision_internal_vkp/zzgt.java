package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzgt extends zzans<zzgt, zzgq> implements zzapg {
    private static final zzgt zzb;
    private zzaob<zzgs> zze = zzI();

    static {
        zzgt zzgtVar = new zzgt();
        zzb = zzgtVar;
        zzans.zzM(zzgt.class, zzgtVar);
    }

    private zzgt() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zze", zzgs.class});
        }
        if (i2 == 3) {
            return new zzgt();
        }
        zzgp zzgpVar = null;
        if (i2 == 4) {
            return new zzgq(zzgpVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
