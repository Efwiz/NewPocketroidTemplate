package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbn extends zzans<zzbn, zzbm> implements zzapg {
    private static final zzbn zzb;
    private int zze;
    private int zzg;
    private zzan zzi;
    private double zzf = 1.0d;
    private float zzh = 0.3f;

    static {
        zzbn zzbnVar = new zzbn();
        zzb = zzbnVar;
        zzans.zzM(zzbn.class, zzbnVar);
    }

    private zzbn() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001က\u0000\u0002င\u0001\u0003ခ\u0002\u0004ဉ\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzbn();
        }
        zzbl zzblVar = null;
        if (i2 == 4) {
            return new zzbm(zzblVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
