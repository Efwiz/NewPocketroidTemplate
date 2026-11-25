package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzain extends zzans<zzain, zzaim> implements zzapg {
    private static final zzain zzb;
    private zzalu zze;

    static {
        zzain zzainVar = new zzain();
        zzb = zzainVar;
        zzans.zzM(zzain.class, zzainVar);
    }

    private zzain() {
    }

    public static zzain zzc() {
        return zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\t", new Object[]{"zze"});
        }
        if (i2 == 3) {
            return new zzain();
        }
        zzaii zzaiiVar = null;
        if (i2 == 4) {
            return new zzaim(zzaiiVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
