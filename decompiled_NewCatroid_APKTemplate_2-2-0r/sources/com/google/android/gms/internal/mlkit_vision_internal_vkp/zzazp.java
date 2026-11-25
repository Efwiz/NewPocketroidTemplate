package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzazp extends zzans<zzazp, zzazo> implements zzapg {
    private static final zzazp zzb;
    private int zze;
    private float zzf = 0.01f;

    static {
        zzazp zzazpVar = new zzazp();
        zzb = zzazpVar;
        zzans.zzM(zzazp.class, zzazpVar);
    }

    private zzazp() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ခ\u0000", new Object[]{"zze", "zzf"});
        }
        if (i2 == 3) {
            return new zzazp();
        }
        zzazn zzaznVar = null;
        if (i2 == 4) {
            return new zzazo(zzaznVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
