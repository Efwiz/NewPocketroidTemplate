package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzasw extends zzans<zzasw, zzasv> implements zzapg {
    private static final zzasw zzb;
    private int zze;
    private int zzf;

    static {
        zzasw zzaswVar = new zzasw();
        zzb = zzaswVar;
        zzans.zzM(zzasw.class, zzaswVar);
    }

    private zzasw() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001င\u0000", new Object[]{"zze", "zzf"});
        }
        if (i2 == 3) {
            return new zzasw();
        }
        zzarm zzarmVar = null;
        if (i2 == 4) {
            return new zzasv(zzarmVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
