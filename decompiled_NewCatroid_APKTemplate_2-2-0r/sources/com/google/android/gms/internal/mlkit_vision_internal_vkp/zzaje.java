package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaje extends zzans<zzaje, zzajd> implements zzapg {
    private static final zzaje zzb;
    private int zze;
    private double zzf;
    private double zzg;

    static {
        zzaje zzajeVar = new zzaje();
        zzb = zzajeVar;
        zzans.zzM(zzaje.class, zzajeVar);
    }

    private zzaje() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001က\u0000\u0002က\u0001", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzaje();
        }
        zzaiv zzaivVar = null;
        if (i2 == 4) {
            return new zzajd(zzaivVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
