package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzald extends zzans<zzald, zzalc> implements zzapg {
    private static final zzald zzb;
    private zzaob<zzakz> zze = zzI();
    private zzaob<String> zzf = zzans.zzI();

    static {
        zzald zzaldVar = new zzald();
        zzb = zzaldVar;
        zzans.zzM(zzald.class, zzaldVar);
    }

    private zzald() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0000\u0001\u000f\u0002\u0000\u0002\u0000\u0001\u001b\u000f\u001a", new Object[]{"zze", zzakz.class, "zzf"});
        }
        if (i2 == 3) {
            return new zzald();
        }
        zzakv zzakvVar = null;
        if (i2 == 4) {
            return new zzalc(zzakvVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
