package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzdd extends zzans<zzdd, zzdc> implements zzapg {
    private static final zzdd zzb;
    private int zze;
    private float zzf = 1.0f;
    private int zzg;

    static {
        zzdd zzddVar = new zzdd();
        zzb = zzddVar;
        zzans.zzM(zzdd.class, zzddVar);
    }

    private zzdd() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ခ\u0000\u0002င\u0001", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzdd();
        }
        zzdb zzdbVar = null;
        if (i2 == 4) {
            return new zzdc(zzdbVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
