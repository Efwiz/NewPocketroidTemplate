package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzalu extends zzans<zzalu, zzalt> implements zzapg {
    private static final zzalu zzb;
    private String zze = "";
    private zzamk zzf = zzamk.zzb;

    static {
        zzalu zzaluVar = new zzalu();
        zzb = zzaluVar;
        zzans.zzM(zzalu.class, zzaluVar);
    }

    private zzalu() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return new zzapq(zzb, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002\n", new Object[]{"zze", "zzf"});
        }
        if (i2 == 3) {
            return new zzalu();
        }
        zzals zzalsVar = null;
        if (i2 == 4) {
            return new zzalt(zzalsVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
