package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzci extends zzans<zzci, zzch> implements zzapg {
    private static final zzci zzb;
    private int zze;
    private zzbw zzf;
    private zzanx zzg = zzF();

    static {
        zzci zzciVar = new zzci();
        zzb = zzciVar;
        zzans.zzM(zzci.class, zzciVar);
    }

    private zzci() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001ဉ\u0000\u0002\u0013", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzci();
        }
        zzcg zzcgVar = null;
        if (i2 == 4) {
            return new zzch(zzcgVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
