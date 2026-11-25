package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzahx extends zzans<zzahx, zzahw> implements zzapg {
    private static final zzahx zzb;
    private int zze;
    private double zzf;
    private double zzg;

    static {
        zzahx zzahxVar = new zzahx();
        zzb = zzahxVar;
        zzans.zzM(zzahx.class, zzahxVar);
    }

    private zzahx() {
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
            return new zzahx();
        }
        zzahu zzahuVar = null;
        if (i2 == 4) {
            return new zzahw(zzahuVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
