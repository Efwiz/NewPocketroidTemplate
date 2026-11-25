package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaw extends zzans<zzaw, zzav> implements zzapg {
    private static final zzaw zzb;
    private int zze;
    private float zzf = 50.0f;
    private int zzg = 1;

    static {
        zzaw zzawVar = new zzaw();
        zzb = zzawVar;
        zzans.zzM(zzaw.class, zzawVar);
    }

    private zzaw() {
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
            return new zzaw();
        }
        zzau zzauVar = null;
        if (i2 == 4) {
            return new zzav(zzauVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
